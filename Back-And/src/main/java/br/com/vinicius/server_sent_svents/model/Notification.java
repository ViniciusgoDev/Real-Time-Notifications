package br.com.vinicius.server_sent_svents.model;

public class Notification {

    private String title;
    private String description;

    public Notification() {
    }

    public Notification(Long id, String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Notification{" +
                ", title='" + title + '\'' +
                ", content='" + description + '\'' +
                '}';
    }
}
