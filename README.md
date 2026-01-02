# Soul AI Portal

Android accessibility service for Soul AI mobile automation.

## 👁️ Overview
Soul AI Portal is an Android accessibility service that provides real-time visual feedback and data collection for UI elements on the screen. It creates an interactive overlay that highlights clickable, checkable, editable, scrollable, and focusable elements, enabling AI-powered mobile automation.

## ✨ Features

### 🔍 Element Detection with Visual Overlay
- Identifies all interactive elements (clickable, checkable, editable, scrollable, and focusable)
- Handles nested elements and scrollable containers
- Assigns unique indices to interactive elements for reference

## 🚀 Usage

### ⚙️ Setup
1. Install the app on your Android device
2. Enable the accessibility service in Android Settings → Accessibility → Soul AI Portal
3. Grant overlay permission when prompted

### 📡 WebSocket Events

Soul AI Portal includes a WebSocket server for real-time event streaming (notifications, etc.).

See the [WebSocket Events documentation](docs/websocket-events.md) for setup and usage.

### 💻 ADB Commands

All commands use the ContentProvider authority `content://com.soulai.portal/`.

#### Query Commands (Reading Data)

```bash
# Test connection (ping)
adb shell content query --uri content://com.soulai.portal/ping

# Get app version
adb shell content query --uri content://com.soulai.portal/version

# Get accessibility tree as JSON (visible elements with overlay indices)
adb shell content query --uri content://com.soulai.portal/a11y_tree

# Get full accessibility tree with ALL properties (complete node info)
adb shell content query --uri content://com.soulai.portal/a11y_tree_full

# Get full tree without filtering small elements (< 1% visibility)
adb shell content query --uri 'content://com.soulai.portal/a11y_tree_full?filter=false'

# Get phone state as JSON (current app, focused element, keyboard visibility)
adb shell content query --uri content://com.soulai.portal/phone_state

# Get combined state (accessibility tree + phone state)
adb shell content query --uri content://com.soulai.portal/state

# Get full combined state (full tree + phone state + device context)
adb shell content query --uri content://com.soulai.portal/state_full

# Get full state without filtering
adb shell content query --uri 'content://com.soulai.portal/state_full?filter=false'

# Get list of installed launchable apps
adb shell content query --uri content://com.soulai.portal/packages
```

#### Insert Commands (Actions & Configuration)

```bash
# Keyboard text input (base64 encoded, clears field first by default)
adb shell content insert --uri content://com.soulai.portal/keyboard/input --bind base64_text:s:"SGVsbG8gV29ybGQ="

# Keyboard text input without clearing the field first
adb shell content insert --uri content://com.soulai.portal/keyboard/input --bind base64_text:s:"SGVsbG8=" --bind clear:b:false

# Clear text in focused input field
adb shell content insert --uri content://com.soulai.portal/keyboard/clear

# Send key event via keyboard (e.g., Enter key = 66, Backspace = 67)
adb shell content insert --uri content://com.soulai.portal/keyboard/key --bind key_code:i:66

# Set overlay vertical offset (in pixels)
adb shell content insert --uri content://com.soulai.portal/overlay_offset --bind offset:i:100

# Toggle overlay visibility (show/hide)
adb shell content insert --uri content://com.soulai.portal/overlay_visible --bind visible:b:true
adb shell content insert --uri content://com.soulai.portal/overlay_visible --bind visible:b:false

# Configure REST API socket server port (default: 8080)
adb shell content insert --uri content://com.soulai.portal/socket_port --bind port:i:8090
```

#### Common Key Codes

| Key | Code | Key | Code |
|-----|------|-----|------|
| Enter | 66 | Backspace | 67 |
| Tab | 61 | Escape | 111 |
| Home | 3 | Back | 4 |
| Up | 19 | Down | 20 |
| Left | 21 | Right | 22 |

### 📤 Data Output
Element data is returned in JSON format through the ContentProvider queries. The response includes a status field and the requested data. All responses follow this structure:

```json
{
  "status": "success",
  "data": "..."
}
```

For error responses:
```json
{
  "status": "error", 
  "error": "Error message"
}
```

## 🔧 Technical Details
- Minimum Android API level: 30 (Android 11.0)
- Uses Android Accessibility Service API
- Implements custom drawing overlay using Window Manager
- Supports multi-window environments
- Built with Kotlin

## 📦 Building

```bash
./gradlew assembleRelease
```

The APK will be in `app/build/outputs/apk/release/`
