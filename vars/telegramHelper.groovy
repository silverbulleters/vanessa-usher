/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.NotificationInfo

/**
 * Отправить уведомления в telegram
 * @param chatId идентификатор канала в telegram
 * @param info информация об уведомлении
 */
void sendNotification(String chatId, NotificationInfo info) {
    message = getSuccessBlock(info)
    telegramSend(message: message, chatId: chatId, )
}

/**
 * Отправить уведомление с ошибкой
 * @param channelId идентификатор канала в slack
 * @param info информация об уведомлении
 */
void sendErrorNotification(String channelId, NotificationInfo info) {

    def message = """ 
        Проект \'${info.projectName}\', сборка #${info.buildNumber}
        *Статус:* ${info.status}
        *Последний коммит:*  ${info.commitId}: ${info.commitMessage}
        *Лог выполнения:* ${info.buildUrl}
    """

    telegramSend(message: message,  chatId: channelId)
}

private getSuccessBlock(NotificationInfo info) {

    def message = """
        *Ветка:* ${info.branchName} 
        *Результат тестирования:*
            Успешно: ${info.successCount},
            Упало: ${info.failedCount}, 
            Пропущено: ${info.skippedCount} 
        Проект \'${info.projectName}\', 
            сборка #${info.buildNumber}
        ${info.buildUrl}
        *Статус:* ${info.status}
   """

    return message
}