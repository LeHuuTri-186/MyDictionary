/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyDictionary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author lehuutri
 */
public class SearchHistory {

    public String getWord() {
        return word;
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }
    
    private String word;
    private LocalDate searchDate;
    
    public SearchHistory(String word, String date) {
        this.word = word;
        this.searchDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
