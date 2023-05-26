package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static bot.TextMessages.*;
import static bot.Buttons.*;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        String start = "/start";
        if (update.hasCallbackQuery()) {
            // Handle callback data
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();
            String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
            int messageId = callbackQuery.getMessage().getMessageId();
            clearPreviousMarkup(chatId, messageId);
            deletePreviousMessage(chatId, messageId);
            if (callbackData.equals(PROFILE_SETUP.getCallBackData())) {
                executeProfileEditState(chatId);
            } else if (callbackData.equals(BACK.getCallBackData())) {
                executeStartState(chatId);
            } else if (callbackData.equals(WRITE_MESSAGE.getCallBackData())) {
                executeStateWithOnlyBackButton(chatId, WRITE_AD_MESSAGE.getMessage());
            } else if (callbackData.equals(PAY_AND_SEND.getCallBackData())) {
                executeStateWithOnlyBackButton(chatId, PAY_N_SEND.getMessage());
            } else if (callbackData.equals(SUPPORT.getCallBackData())) {
                executeStateWithOnlyBackButton(chatId, SUPPORT_MESSAGE.getMessage());
            } else if (callbackData.equals(SHARE_CHAT.getCallBackData())) {
                executeStateWithOnlyBackButton(chatId, SHARE_LINK_TO_CHAT.getMessage());
            } else {
                executeStateWithOnlyBackButton(chatId, ERROR_MESSAGE.getMessage());
            }
        } else if ((update.hasMessage())) {
            Message inMess = update.getMessage();
            String chatId = inMess.getChatId().toString();
            String userName = getUserName(inMess);
            String inMessText = inMess.getText();
            if (inMessText.equals(start)) {
                executeStartState(chatId);
            } else {
                executeStateWithOnlyBackButton(chatId, ERROR_MESSAGE.getMessage());
            }
        }
    }

    public String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        if (userName != null) {
            return userName;
        } else {
            return String.format("%s %s", user.getLastName(), user.getFirstName());
        }
    }

    private void executeStartState(String chatId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        List<InlineKeyboardButton> row5 = new ArrayList<>();
        List<InlineKeyboardButton> row6 = new ArrayList<>();
        row1.add(DetailsFactory.getButton(PROFILE_SETUP));
        row2.add(DetailsFactory.getButton(WRITE_MESSAGE));
        row3.add(DetailsFactory.getButton(SHARE_CHAT));
        row4.add(DetailsFactory.getButton(SUPPORT));
        row5.add(DetailsFactory.getButton(SEND_TEST_MESSAGE));
        row6.add(DetailsFactory.getButton(PAY_AND_SEND));
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        keyboardRowList.add(row3);
        keyboardRowList.add(row4);
        keyboardRowList.add(row5);
        keyboardRowList.add(row6);
        keyboardMarkup.setKeyboard(keyboardRowList);
        // Create a message object
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText(GREETINGS_MESSAGE.getMessage());
        message.setReplyMarkup(keyboardMarkup);
        try {
            // Send the message
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void executeProfileEditState(String chatId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row1.add(DetailsFactory.getButton(UPLOAD_PHOTO));
        row2.add(DetailsFactory.getButton(CHANGE_PROFILE_DESCRIPTION));
        row3.add(DetailsFactory.getButton(CHANGE_PROFILE_NAME));
        row4.add(DetailsFactory.getButton(BACK));
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        keyboardRowList.add(row3);
        keyboardRowList.add(row4);
        keyboardMarkup.setKeyboard(keyboardRowList);
        // Create a message object
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText(SETUP_PROFILE.getMessage());
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeStateWithOnlyBackButton(String chatId, String messageText) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(DetailsFactory.getButton(BACK));
        keyboardRowList.add(row1);
        keyboardMarkup.setKeyboard(keyboardRowList);
        // Create a message object
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText(messageText);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearPreviousMarkup(String chatId, int messageId) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(null);

        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePreviousMessage(String chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "KeyAdvertisingBot";
    }

    @Override
    public String getBotToken() {
        return "5830821147:AAH5UyMdqfX7J1nImaD2ns3eFaxM8PFsGW8";
    }
}
