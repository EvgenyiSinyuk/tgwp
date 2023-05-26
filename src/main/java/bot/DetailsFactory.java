package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class DetailsFactory {

    public static InlineKeyboardButton getButton(Buttons buttonInfo) {
        InlineKeyboardButton button = new InlineKeyboardButton(buttonInfo.getName());
        button.setCallbackData(buttonInfo.getCallBackData());
        return button;
    }
}
