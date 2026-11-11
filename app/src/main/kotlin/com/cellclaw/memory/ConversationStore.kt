
package com.cellclaw.memory

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationStore @Inject constructor(
    private val messageDao: MessageDao
) {
    private var currentConversationId = "default"

    suspend fun addMessage(role: String, content: String) {
        messageDao.insert(
            MessageEntity(
                role = role,
                content = content,
                conversationId = currentConversationId
            )
        )
    }

    suspend fun getRecentMessages(limit: Int = 50): List<MessageEntity> {
        return messageDao.getRecent(currentConversationId, limit).reversed()
    }

    suspend fun getAllMessages(): List<MessageEntity> {
        return messageDao.getAll(currentConversationId)
    }

    suspend fun getConversationIds(): List<String> {
        return messageDao.getConversationIds()
    }

    suspend fun editMessage(messageId: Long, content: String): Boolean {
        return messageDao.updateMessage(
            messageId,
            currentConversationId,
            content
        ) > 0
    }

    suspend fun deleteMessage(messageId: Long): Boolean {
        return messageDao.deleteMessage(
            messageId,
            currentConversationId
        ) > 0
    }

    suspend fun clearCurrentConversation() {
        messageDao.clearConversation(currentConversationId)
    }

    suspend fun messageCount(): Int {
        return messageDao.count(currentConversationId)
    }

    fun newConversation(): String {
        val id = UUID.randomUUID().toString()
        currentConversationId = id
        return id
    }

    fun newConversation(id: String) {
        currentConversationId = id
    }

    fun switchConversation(id: String) {
        currentConversationId = id
    }

    fun currentId(): String = currentConversationId
}
