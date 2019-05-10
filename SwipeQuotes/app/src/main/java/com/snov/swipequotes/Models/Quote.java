package com.snov.swipequotes.Models;

public class Quote {
    public String quoteId;
    public String quote;
    public String author;
    public String timestamp;

    public Quote(String quoteId, String quote, String author, String timestamp) {
        this.quoteId = quoteId;
        this.quote = quote;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
