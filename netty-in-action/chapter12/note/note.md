# 第 12 章 WebSocket

## 12.3 添加 WebSocket 支持

在从标准的 HTTP 或者 HTTPS 协议切换到 WebSocket 时，将会使用一种称为升级握手的机制。因此，使用 WebSocket 的应用程序将始终以 HTTP 或者 HTTPS 作为开始，然后再执行升级。这个升级动作发生的确切时刻特定于应用程序，它可能会发生在启动时，也可能会发生在请求了某个特定的 URL 之后。

