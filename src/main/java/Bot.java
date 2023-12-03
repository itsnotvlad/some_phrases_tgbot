import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = "6752654516:AAEkD7o3pMdqvPw3JWCeMn8ydQfcdAWIAuo";
    final private String BOT_NAME = "some_phrases_tgbot";
    Storage storage;
    ReplyKeyboardMarkup replyKeyboardMarkup;

    Bot() {
        storage = new Storage();
    }

    // Some another comment from testBranch

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    // Some comment

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                // Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                // Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                // Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inMess.getText());
                // Создаем объект класса SendMessage - наш будущий ответ пользователю
                SendMessage outMess = new SendMessage();

                // Добавляем в наше сообщение id чата, а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);
                outMess.setReplyMarkup(replyKeyboardMarkup);

                // Отправка в чат
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public String parseMessage(String textMsg) {
        String response;

        // Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (textMsg.equals("/start")) {
            response = "Приветствую, бот знает много цитат. Жми /get, чтобы получить случайную из них";
        }
        else if (textMsg.equals("/get") || textMsg.equals("Просвяти")) {
            response = storage.getRandQuote();
        }
        else {
            response = "Сообщение не распознано";
        }
        return response;
    }
}
