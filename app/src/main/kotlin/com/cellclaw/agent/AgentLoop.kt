
suspend fun loadHistory() {
    conversationHistory.clear()

    val stored = conversationStore.getRecentMessages(50)

    for (msg in stored) {
        val role = if (msg.role == "user") {
            Role.USER
        } else {
            Role.ASSISTANT
        }

        conversationHistory.add(
            Message(role, listOf(ContentBlock.Text(msg.content)))
        )
    }
}
