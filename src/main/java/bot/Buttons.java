package bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Buttons {

    PROFILE_SETUP("Настроить профиль для рассылки", "profile"),
    SUPPORT("Написать в поддержку бота", "support"),
    WRITE_MESSAGE("Написать сообщение для рассылки", "ad_message"),
    SHARE_CHAT("Отправить ссылку на чат, для пользователей которого будет рассылка", "share_chat"),
    PAY_AND_SEND("Оплатить и отправить", "paynsend"),
    BACK("Назад в главное меню", "back"),
    UPLOAD_PHOTO("Загрузить фото профиля", "upload_photo"),
    CHANGE_PROFILE_NAME("Изменить имя профиля", "change_profile_name"),
    SEND_TEST_MESSAGE("Отправить сообщение самому себе чтобы посмотреть как оно выглядит и как выглядит рассылка(бесплатно)", "send_test_message"),
    CHANGE_PROFILE_DESCRIPTION("Изменить описание профиля", "change_profile_name");

    @Getter
    private String name;

    @Getter
    private String callBackData;

}
