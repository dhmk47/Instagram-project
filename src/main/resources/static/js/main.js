window.onload = () => {
    EventSetter.getInstance();
    FileUploader.getInstance();
    BoardContent.getInstance().setContentKeyPressEvent();
}