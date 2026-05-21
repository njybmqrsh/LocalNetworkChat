package com.localnetworkchat.data.network

import android.content.Context
import android.net.wifi.WifiManager
import kotlinx.coroutines.*
import java.io.*
import java.net.*

class DeviceDiscoveryManager(private val context: Context) {
    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
    
    companion object {
        private const val BROADCAST_PORT = 9876
        private const val DISCOVERY_MESSAGE = "LOCALCHAT_DISCOVERY_REQUEST"
        private const val DISCOVERY_RESPONSE = "LOCALCHAT_DISCOVERY_RESPONSE"
        private const val TIMEOUT = 5000L
    }

    suspend fun discoverDevices(localDeviceInfo: String): List<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            val broadcastAddress = getBroadcastAddress()
            val discoveredDevices = mutableListOf<String>()
            
            sendDiscoveryBroadcast(broadcastAddress, localDeviceInfo)
            val responses = listenForResponses()
            discoveredDevices.addAll(responses)
            
            discoveredDevices.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun getBroadcastAddress(): String {
        val dhcp = wifiManager.dhcpInfo
        val broadcast = dhcp.gateway or (dhcp.netmask.inv())
        return intToInetAddress(broadcast).hostAddress ?: "255.255.255.255"
    }

    private fun intToInetAddress(hostAddress: Int): InetAddress {
        return InetAddress.getByName(
            String.format(
                "%d.%d.%d.%d",
                hostAddress and 0xff,
                hostAddress shr 8 and 0xff,
                hostAddress shr 16 and 0xff,
                hostAddress shr 24 and 0xff
            )
        )
    }

    private suspend fun sendDiscoveryBroadcast(broadcastAddress: String, deviceInfo: String) {
        withContext(Dispatchers.IO) {
            try {
                DatagramSocket().use { socket ->
                    socket.broadcast = true
                    val message = "$DISCOVERY_MESSAGE|$deviceInfo"
                    val data = message.toByteArray()
                    val packet = DatagramPacket(
                        data, data.size,
                        InetAddress.getByName(broadcastAddress),
                        BROADCAST_PORT
                    )
                    socket.send(packet)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun listenForResponses(): List<String> {
        return withContext(Dispatchers.IO) {
            val responses = mutableListOf<String>()
            try {
                DatagramSocket(BROADCAST_PORT).use { socket ->
                    socket.soTimeout = TIMEOUT.toInt()
                    val buffer = ByteArray(1024)
                    
                    try {
                        while (true) {
                            val packet = DatagramPacket(buffer, buffer.size)
                            socket.receive(packet)
                            val message = String(packet.data, 0, packet.length)
                            if (message.startsWith(DISCOVERY_RESPONSE)) {
                                responses.add(packet.address.hostAddress + "|" + message.substringAfter("|"))
                            }
                        }
                    } catch (e: SocketTimeoutException) {
                        // Expected timeout
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            responses.toList()
        }
    }

    fun startDiscoveryListener(localDeviceInfo: String) {
        coroutineScope.launch {
            try {
                DatagramSocket(BROADCAST_PORT).use { socket ->
                    val buffer = ByteArray(1024)
                    
                    while (isActive) {
                        try {
                            val packet = DatagramPacket(buffer, buffer.size)
                            socket.receive(packet)
                            val message = String(packet.data, 0, packet.length)
                            if (message.startsWith(DISCOVERY_MESSAGE)) {
                                val responseMessage = "$DISCOVERY_RESPONSE|$localDeviceInfo"
                                val responseData = responseMessage.toByteArray()
                                val responsePacket = DatagramPacket(
                                    responseData, responseData.size,
                                    packet.address, packet.port
                                )
                                socket.send(responsePacket)
                            }
                        } catch (e: Exception) {
                            if (isActive) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stop() {
        coroutineScope.cancel()
    }
}
