# LocalNetworkChat 🚀

A comprehensive Android application for **local network communication** with support for:
- 💬 **Text Messaging** (Private & Public Groups)
- ☎️ **Voice Calls**
- 📹 **Video Calls**
- 🔍 **Device Discovery**
- 👥 **User Management**
- 🚫 **Ban System**

## ✨ Features

### Core Features
- **Automatic Device Discovery** - Find devices on your LAN instantly
- **Private Messaging** - One-to-one encrypted-ready messaging
- **Public Groups** - Create open chat groups for community discussions
- **Private Groups** - Invite-only group conversations
- **Voice Calls** - Crystal clear VoIP over local network
- **Video Calls** - Face-to-face communication without cloud
- **User Profiles** - Set status, avatar, and bio
- **Ban Management** - Block users at individual or group level
- **Online Status** - Real-time presence detection
- **Message History** - Local storage of all conversations

## 🏗️ Architecture

```
┌─────────────────────────────────┐
│   Presentation (Jetpack Compose)│
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│  ViewModel & State Management   │
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│    Repository Pattern Layer     │
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│  Network & Local Storage Layer  │
├─────────────────────────────────┤
│ • Device Discovery (UDP)        │
│ • Chat Network (TCP)            │
│ • Voice/Video (WebRTC)          │
│ • Local Database (Room)         │
└─────────────────────────────────┘
```

## 📦 Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin 1.9.0+ |
| UI Framework | Jetpack Compose |
| Async | Coroutines & Flow |
| Networking | Socket, Retrofit, OkHttp |
| Voice/Video | WebRTC |
| Database | Room |
| Preferences | DataStore |
| Dependency Injection | Hilt (Ready) |

## 📋 Network Protocols

### Device Discovery
```
Port: 9876 (UDP)
Request: LOCALCHAT_DISCOVERY_REQUEST|{deviceInfo}
Response: LOCALCHAT_DISCOVERY_RESPONSE|{deviceInfo}
```

### Chat Messages
```
Port: 9877 (TCP)
Format: {senderId}|{recipientId}|{groupId}|{message}
```

### Voice/Video Calls
```
Port: 9879 (TCP + WebRTC)
Invite: {callId}|{callerId}|{callerName}|{callType}
Response: {callId}|{status}
```

## 🗂️ Project Structure

```
app/src/main/java/com/localnetworkchat/
├── ui/
│   ├── screens/           # Main UI screens
│   │   ├── ChatListScreen
│   │   ├── ContactsScreen
│   │   ├── GroupsScreen
│   │   └── SettingsScreen
│   ├── theme/             # Material Design 3 theme
│   └── components/        # Reusable UI components
├── data/
│   ├── network/           # Network managers
│   │   ├── DeviceDiscoveryManager
│   │   ├── ChatNetworkManager
│   │   └── VoiceVideoCallManager
│   ├── database/          # Room database DAOs
│   ├── model/             # Data classes
│   │   ├── User
│   │   ├── ChatMessage
│   │   ├── ChatGroup
│   │   ├── CallSession
│   │   └── BannedUser
│   └── repository/        # Repository pattern
├── viewmodel/             # ViewModels (coming soon)
└── MainActivity.kt
```

## 🔐 Required Permissions

```xml
<!-- Network -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

<!-- Location (for network discovery) -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<!-- Audio/Video -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.CAMERA" />

<!-- Optional: Bluetooth -->
<uses-permission android:name="android.permission.BLUETOOTH" />
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Giraffe or later
- Android SDK 34
- Minimum API Level 24
- Kotlin 1.9+

### Installation

1. **Clone Repository**
```bash
git clone https://github.com/njybmqrsh/LocalNetworkChat.git
cd LocalNetworkChat
```

2. **Open in Android Studio**
   - File → Open → Select project

3. **Sync Gradle**
   - Wait for Gradle sync to complete

4. **Build & Run**
   - Connect device or start emulator
   - Click "Run" or press Shift+F10

## 🔧 Configuration

### gradle/libs.versions.toml (Planned)
```toml
[versions]
android = "8.2"
kotlin = "1.9.0"
compose = "1.5.4"
```

## 📱 UI Screens (Implementation Map)

- [ ] **Home/Chats** - List of active conversations
- [ ] **Chat Detail** - Individual chat with message input
- [ ] **Contacts** - List of discovered devices
- [ ] **Contact Detail** - User profile & action buttons
- [ ] **Groups** - List of public/private groups
- [ ] **Create Group** - Group creation wizard
- [ ] **Voice Call** - Ringing & in-call interface
- [ ] **Video Call** - Video preview & controls
- [ ] **Settings** - User profile & preferences
- [ ] **Ban Management** - Block user interface

## 🎯 Development Roadmap

### Phase 1: Core Networking ✅
- [x] Device discovery
- [x] Message sending
- [x] Call signaling

### Phase 2: UI Implementation (In Progress)
- [ ] Main screens scaffold
- [ ] Chat interface
- [ ] Contacts list
- [ ] Call interface

### Phase 3: Features
- [ ] Database persistence
- [ ] WebRTC integration
- [ ] Audio/video codec handling
- [ ] Notification system

### Phase 4: Polish
- [ ] Error handling
- [ ] Performance optimization
- [ ] Testing
- [ ] Documentation

## 🤝 Contributing

Contributions welcome! Please:
1. Fork repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 License

MIT License - See LICENSE file for details

## 💬 Support

For issues, questions, or suggestions:
- Open an Issue on GitHub
- Create a Discussion
- Submit a Pull Request

---

**Made with ❤️ by the LocalNetworkChat Team**
