/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MyDictionaryGUI;

import java.awt.event.ActionEvent;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import MyDictionary.MyDictionary;
import MyDictionary.SearchHistory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author lehuutri
 */
public class MyDictionaryGUI extends javax.swing.JFrame {

    /**
     * Creates new form MyDictionaryGUI
     */   
    MyDictionary myDictionary;
    boolean VieEn = false;
    boolean VieFav = false;
    boolean favAscending = true;
    boolean isEng = true;
    
    public void changeLanguage() {
        if (!isEng) {
            settingOption.setText("Cài đặt");
            language.setText("Ngôn ngữ");
            EngLang.setText("Anh");
            vieLang.setText("Việt");
            aboutMe.setText("Về tôi");
            
            tools.setText("Công cụ");
            addWordBtn.setText("Thêm từ");
            deleteBtn.setText("Xoá từ");
            addFav.setText("Yêu thích");
            
            searchBtn.setText("Tìm");
            EnVieR.setText("Anh - Việt");
            VieEnR.setText("Việt - Anh");
            searchResult.setText("Kết quả tìm kiếm:");
            Information.setText("Thông tin");
            
            JTabbedPane tabbedPane = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, meaningTab);
            tabbedPane.setTitleAt(0, "Ý nghĩa");
            tabbedPane.setTitleAt(1, "Yêu thích");
            tabbedPane.setTitleAt(2, "Thống kê");
            
            EngFavR.setText("Anh");
            vieFavR.setText("Việt");
            removeBtn.setText("Bỏ từ");      
            
            startOn.setText("Từ ngày:");
            endOn.setText("Đến ngày:");
            
            JTableHeader header = history.getTableHeader();
            TableColumnModel colMod = header.getColumnModel();
            colMod.getColumn(0).setHeaderValue("Từ vựng");
            colMod.getColumn(1).setHeaderValue("Số lần tra");
            header.setFont(new java.awt.Font("SVN-Aguda", 0, 14));
            header.repaint();
        } else {
            settingOption.setText("Settings");
            language.setText("Language");
            EngLang.setText("Eng");
            vieLang.setText("Vie");
            aboutMe.setText("About me");
            
            tools.setText("Tools");
            addWordBtn.setText("Add word");
            deleteBtn.setText("Delete word");
            addFav.setText("Add to favorites");
            
            searchBtn.setText("Search");
            EnVieR.setText("En - Vie");
            VieEnR.setText("Vie - En");
            searchResult.setText("Search Result:");
            meaningTab.setName("Meaning");
            Information.setText("Information");
            
            JTabbedPane tabbedPane = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, meaningTab);
            tabbedPane.setTitleAt(0, "Meaning");
            tabbedPane.setTitleAt(1, "Favorites");
            tabbedPane.setTitleAt(2, "Statistics");
            
            EngFavR.setText("Eng");
            vieFavR.setText("Vie");
            removeBtn.setText("Remove entry"); 
            
            startOn.setText("From date:");
            endOn.setText("To date:");
            
                        
            JTableHeader header = history.getTableHeader();
            TableColumnModel colMod = header.getColumnModel();
            colMod.getColumn(0).setHeaderValue("Word");
            colMod.getColumn(1).setHeaderValue("Search count");
            header.setFont(new java.awt.Font("SVN-Aguda", 0, 14));
            header.repaint();
        }
    }
    
    public void makeChange() {    
        DefaultTableModel model = (DefaultTableModel) history.getModel();
        
        while (model.getRowCount() > 0)
        {
            model.removeRow(0);
        }
                
        if (VieFav) {
            TreeSet<String> tr = this.myDictionary.getVieFavs();
            
            if (favAscending) {
                this.favList.setListData(tr.toArray(new String[tr.size()]));
            } else {
                this.favList.setListData(tr.descendingSet().toArray(new String[tr.size()]));
            }
        } else {
            TreeSet<String> tr = this.myDictionary.getEngFavs();
            
            if (favAscending) {
                this.favList.setListData(tr.toArray(new String[tr.size()]));
            } else {
                this.favList.setListData(tr.descendingSet().toArray(new String[tr.size()]));
            }
        }
        
        if (searchField.getText().equals("")) {
            wordList.setListData(new String[]{});
            return;
        }

        if (VieEn) {
            wordList.setListData(myDictionary.findVieWordsStartWith(searchField.getText()));

            return;
        }

        wordList.setListData(myDictionary.findEngWordsStartWith(searchField.getText()));
    }
    
      
    public MyDictionaryGUI() {
        initComponents();
        
        this.myDictionary = MyDictionary.getInstance();
        
        this.EnVieR.setSelected(true);
        this.VieEnR.setSelected(false);
        this.EngFavR.setSelected(true);
        this.vieFavR.setSelected(false);
        this.AZBtn.setSelected(true);
        this.ZABtn.setSelected(false);
        this.startDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.endDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        this.changeLanguage();

        
        this.Information.addActionListener(e -> {
            JOptionPane.showMessageDialog(rootPane, "Lê Hữu Trí - 21120186\nContact: lehuutri.business@gmail.com\nMyDictionary Ver 1.0");
        });
        
        this.EngLang.addActionListener(e->{
            if (EngLang.isSelected()) {
                isEng = true;
                
                changeLanguage();
            }
        });
        
        this.vieLang.addActionListener(e->{
            if (vieLang.isSelected()) {
                isEng = false;
                
                changeLanguage();
            }
        });
        
        
        this.searchHistory.addActionListener(e -> {
            
            LocalDate startOn = LocalDate.parse(
                            startDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate endOn = LocalDate.parse(
                            endDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            if (startOn.isAfter(endOn)) {
                if (isEng)
                    JOptionPane.showMessageDialog(rootPane, "Invalid date!");
                else 
                    JOptionPane.showMessageDialog(rootPane, "Ngày không hợp lệ!");

                
                return;
            }
            
            if (startOn.isAfter(LocalDate.now()) || endOn.isAfter(LocalDate.now()))
            {
                if (isEng)
                    JOptionPane.showMessageDialog(rootPane, "Invalid date!");
                else 
                    JOptionPane.showMessageDialog(rootPane, "Ngày không hợp lệ!");

                
                return;
            }
            
            DefaultTableModel model = (DefaultTableModel) history.getModel();
        
            while (model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
        
            List<SearchHistory> his = myDictionary.getSearchHistory(startOn, endOn);
                    
            while(his.size() > 0)
            {   
                int count = 1;
                for (int j = 1; j < his.size(); j++)
                {
                    if (his.get(j).getWord().equals(his.get(0).getWord())) {
                        count++;
                        his.remove(j);
                        j--;
                    }
                }
                model.addRow(new Object[]{his.get(0).getWord(), count});
                his.remove(0);
                
                
            }
        });
        
        this.removeBtn.addActionListener(e -> {
            String word = searchField.getText();
            
            if (word.equals("")) {
                if (isEng)
                    JOptionPane.showMessageDialog(rootPane, "Please choose a word!");
                else
                    JOptionPane.showMessageDialog(rootPane, "Hãy chọn một từ!");
                
                return;
            }
            
            boolean result = false;
            
            if (VieFav) {
                result = this.myDictionary.deleteFavVie(word);
            } else {
                result = this.myDictionary.deleteFavEng(word);
            }
            
            if (result) {
                makeChange();
                if (isEng)
                    JOptionPane.showMessageDialog(rootPane, "Successfully removed: " + word);
                else
                    JOptionPane.showMessageDialog(rootPane, "Xoá thành công: " + word);
                
                return;
            }
            if (isEng)
                JOptionPane.showMessageDialog(rootPane, "Failed to remove: " + word);
            else
                JOptionPane.showMessageDialog(rootPane, "Xoá không thành công: " + word);
        });
        
        this.EngFavR.addActionListener(e -> {
            if (EngFavR.isSelected()) {
                VieFav = false;
                makeChange();
            }
        });
        
        this.vieFavR.addActionListener(e -> {
            if (vieFavR.isSelected()) {
                VieFav = true;
                makeChange();
            }
        });
        
        this.AZBtn.addActionListener(e -> {
            if (AZBtn.isSelected()) {
                favAscending = true;
                makeChange();
            }
        });
        
        this.ZABtn.addActionListener(e -> {
            if (ZABtn.isSelected()) {
                favAscending = false;
                makeChange();
            }
        });
        
        this.addFav.addActionListener(e -> {
            boolean result = false;
            
            String word = searchField.getText();
            String meaning = null;
            
            meaning = myDictionary.findEngWord(word);
            
            if (VieEn) {
                meaning = myDictionary.findVieWord(word);
            }
            
            if (meaning == null)
            {
                if (isEng)
                    JOptionPane.showMessageDialog(rootPane, "Please choose a word!");
                else
                    JOptionPane.showMessageDialog(rootPane, "Hãy chọn một từ!");
               
                return;
            }

            
            if (VieEn) {
               result = this.myDictionary.addFavVie(word);
            } else {
               result = this.myDictionary.addFavEng(word);
            }
            
            if (result) {
                makeChange();
                if (isEng)
                    JOptionPane.showMessageDialog(rootPane, "Successfully added: " + word);
                else
                    JOptionPane.showMessageDialog(rootPane, "Thêm thành công: " + word);

                return;
            }
            if (isEng)
                JOptionPane.showMessageDialog(rootPane, "Failed to add: " + word);
            else
                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại: " + word);

        });
        
        this.makeChange();
        
        this.deleteBtn.addActionListener(e -> {
            String word = searchField.getText().strip();
            
            if (word.equals("")) {
                if (isEng)
                    JOptionPane.showMessageDialog(rootPane, "Please choose a word!");
                else
                    JOptionPane.showMessageDialog(rootPane, "Hãy chọn một từ!");
               
                return;
            }
            
            boolean result = false;

            if (VieEn) {
                result = myDictionary.deleteVietWord(word);
            } else {
                result = myDictionary.deleteEngWord(word);
            }
            
            if (result) {
                if (isEng)
                    JOptionPane.showMessageDialog(rootPane, "Successfully delete: " + word);
                else 
                    JOptionPane.showMessageDialog(rootPane, "Xoá thành công: " + word);                    
                
                if (VieEn) {
                    myDictionary.deleteFavVie(word);
                } else {
                    myDictionary.deleteFavEng(word);
                }
                
                meaningText.setText("");
                makeChange();
                
                return;
            }
            
            if (isEng)
                JOptionPane.showMessageDialog(rootPane, "Failed to delete: " + word);
            else 
                JOptionPane.showMessageDialog(rootPane, "Xoá thất bại: " + word);  
        });
        
        this.searchBtn.addActionListener(e -> { 
            String word = searchField.getText();
            String meaning = null;
            
            meaning = myDictionary.findEngWord(word);
            
            if (VieEn) {
                meaning = myDictionary.findVieWord(word);
            }
            
            if (meaning != null)
            {
                meaningText.setText(meaning);
                myDictionary.addToHistory(new SearchHistory(word, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                
               
               return;
            }
            
            if (isEng)
                JOptionPane.showMessageDialog(this, "Word does not exist");
            else
                JOptionPane.showMessageDialog(this, "Từ không tồn tại");

        });
        
        this.favList.addListSelectionListener(e -> {
            if (favList.getSelectedIndex() == -1) {
                return;
            }
            
            String word = favList.getSelectedValue();
            String meaning = null;
            
            meaning = myDictionary.findEngWord(word);
            
            if (meaning != null)
            {
                VieEn = false;
                VieEnR.setSelected(VieEn);
                EnVieR.setSelected(true);
                
                if (!e.getValueIsAdjusting())
                {
                meaningText.setText(meaning);
                searchField.setText(word);
                myDictionary.addToHistory(new SearchHistory(word, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));  
                }
                
                return;
            }
                        
            meaning = myDictionary.findVieWord(word);
            
            
            if (meaning == null) {
                if (isEng)
                    JOptionPane.showMessageDialog(this, "Word does not exist");
                else
                    JOptionPane.showMessageDialog(this, "Từ không tồn tại");

                return;
            }
            
            VieEn = true;
            VieEnR.setSelected(VieEn);
            EnVieR.setSelected(false);
            
            
            if (!e.getValueIsAdjusting())
            {
                meaningText.setText(meaning);
                searchField.setText(word);
                myDictionary.addToHistory(new SearchHistory(word, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));  
            }
        });
        
        this.addWordBtn.addActionListener((ActionEvent e) -> {
            AddWordDialog d = new AddWordDialog(this, true, isEng);
            String[] result = d.showDialog();
            boolean wordAdded = false;
            
            if (result == null) {
                return;
            }
            
            if (VieEn) {
                wordAdded = myDictionary.addVietWord(result[0], result[1]);
            } else {
                wordAdded = myDictionary.addEngWord(result[0], result[1]);
            }
                
            if (wordAdded) {
                if (isEng)
                    JOptionPane.showMessageDialog(this, "New word added");
                else 
                    JOptionPane.showMessageDialog(this, "Vừa thêm từ mới");

                makeChange();
                return;
            }
            if (isEng)
                JOptionPane.showMessageDialog(this, "This word has already existed");
            else 
                JOptionPane.showMessageDialog(this, "Từ đã tồn tại");

        });
        
        this.EnVieR.addActionListener(e -> {
            if (this.EnVieR.isSelected())
            {
                VieEn = false;
                makeChange();
            }
        });
        
        this.VieEnR.addActionListener(e -> {
            if (this.VieEnR.isSelected())
            {
                VieEn = true;
                makeChange();
            }
        });
        
        this.wordList.addListSelectionListener(e -> {
            if (wordList.getSelectedIndex() == -1) {
                return;
            }
            
            String word = wordList.getSelectedValue();
            String meaning = null;
            
            meaning = myDictionary.findEngWord(word);
            
            if (VieEn) {
                meaning = myDictionary.findVieWord(word);
            }
            
            if (meaning == null) {
                if (isEng)
                    JOptionPane.showMessageDialog(this, "Word does not exist");
                else
                    JOptionPane.showMessageDialog(this, "Từ không tồn tại");

                return;
            }
            
            if (!e.getValueIsAdjusting())
            {
                meaningText.setText(meaning);
                searchField.setText(word);
                myDictionary.addToHistory(new SearchHistory(word, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));  
            }
        });
        
        this.searchField.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                makeChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                makeChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                makeChange();
            }           
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jPanel4 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jDialog1 = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        EnVieR = new javax.swing.JRadioButton();
        VieEnR = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        addWordBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        addFav = new javax.swing.JButton();
        tools = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        wordList = new javax.swing.JList<>();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        meaningTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        meaningText = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        AZBtn = new javax.swing.JRadioButton();
        ZABtn = new javax.swing.JRadioButton();
        removeBtn = new javax.swing.JButton();
        EngFavR = new javax.swing.JRadioButton();
        vieFavR = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        favList = new javax.swing.JList<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        history = new javax.swing.JTable();
        startDate = new javax.swing.JFormattedTextField();
        endDate = new javax.swing.JFormattedTextField();
        startOn = new javax.swing.JLabel();
        endOn = new javax.swing.JLabel();
        searchHistory = new javax.swing.JButton();
        searchResult = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        settingOption = new javax.swing.JMenu();
        language = new javax.swing.JMenu();
        EngLang = new javax.swing.JRadioButtonMenuItem();
        vieLang = new javax.swing.JRadioButtonMenuItem();
        aboutMe = new javax.swing.JMenu();
        Information = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar2.add(jMenu1);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("File");
        jMenuBar3.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar3.add(jMenu5);

        jMenu6.setText("File");
        jMenuBar4.add(jMenu6);

        jMenu7.setText("Edit");
        jMenuBar4.add(jMenu7);

        jMenu8.setText("jMenu8");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
        );

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setTitle("Add new word");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyDictionary");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(166, 209, 255));

        jPanel2.setBackground(new java.awt.Color(89, 52, 213));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(89, 52, 213), 10, true));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SVN-Kimberley", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("MyDictionary");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        jLabel2.setIconTextGap(0);

        searchField.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        searchField.setToolTipText("Search...");

        searchBtn.setBackground(new java.awt.Color(255, 255, 255));
        searchBtn.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        searchBtn.setForeground(new java.awt.Color(90, 86, 233));
        searchBtn.setText("Search");

        buttonGroup1.add(EnVieR);
        EnVieR.setFont(new java.awt.Font("SVN-Aguda", 0, 14)); // NOI18N
        EnVieR.setForeground(new java.awt.Color(255, 255, 255));
        EnVieR.setText("En - Vie");
        EnVieR.setActionCommand("Eng - Vie");

        buttonGroup1.add(VieEnR);
        VieEnR.setFont(new java.awt.Font("SVN-Aguda", 0, 14)); // NOI18N
        VieEnR.setForeground(new java.awt.Color(255, 255, 255));
        VieEnR.setSelected(true);
        VieEnR.setText("Vie - En");

        jPanel6.setBackground(new java.awt.Color(100, 52, 245));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addWordBtn.setBackground(new java.awt.Color(0, 204, 0));
        addWordBtn.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        addWordBtn.setForeground(new java.awt.Color(0, 0, 102));
        addWordBtn.setText("Add Word");

        deleteBtn.setBackground(new java.awt.Color(255, 0, 0));
        deleteBtn.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtn.setText("Delete word");
        deleteBtn.setActionCommand("Delete Word");
        deleteBtn.setInheritsPopupMenu(true);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        addFav.setBackground(new java.awt.Color(255, 153, 255));
        addFav.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        addFav.setForeground(new java.awt.Color(0, 0, 204));
        addFav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        addFav.setText("Add to Favorites");
        addFav.setActionCommand("Delete Word");
        addFav.setInheritsPopupMenu(true);
        addFav.setMargin(new java.awt.Insets(0, 0, 0, 0));
        addFav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFavActionPerformed(evt);
            }
        });

        tools.setBackground(new java.awt.Color(255, 255, 255));
        tools.setFont(new java.awt.Font("SVN-Kimberley", 0, 24)); // NOI18N
        tools.setForeground(new java.awt.Color(255, 255, 255));
        tools.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tools.setText("Tools");
        tools.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(addWordBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(addFav, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tools, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(tools)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addWordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addFav, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(EnVieR)
                        .addGap(18, 18, 18)
                        .addComponent(VieEnR))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtn)))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(searchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EnVieR)
                            .addComponent(VieEnR))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        wordList.setFont(new java.awt.Font("SVN-Aguda", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(wordList);

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane2.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N

        meaningText.setEditable(false);
        meaningText.setColumns(20);
        meaningText.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        meaningText.setLineWrap(true);
        meaningText.setRows(5);
        meaningText.setFocusable(false);
        jScrollPane2.setViewportView(meaningText);

        javax.swing.GroupLayout meaningTabLayout = new javax.swing.GroupLayout(meaningTab);
        meaningTab.setLayout(meaningTabLayout);
        meaningTabLayout.setHorizontalGroup(
            meaningTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(meaningTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );
        meaningTabLayout.setVerticalGroup(
            meaningTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(meaningTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Meaning", meaningTab);

        buttonGroup2.add(AZBtn);
        AZBtn.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        AZBtn.setForeground(new java.awt.Color(0, 0, 0));
        AZBtn.setText("A - Z");

        buttonGroup2.add(ZABtn);
        ZABtn.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        ZABtn.setForeground(new java.awt.Color(0, 0, 0));
        ZABtn.setText("Z - A");

        removeBtn.setBackground(new java.awt.Color(89, 50, 213));
        removeBtn.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        removeBtn.setForeground(new java.awt.Color(255, 255, 255));
        removeBtn.setText("Remove entry");
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        buttonGroup3.add(EngFavR);
        EngFavR.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        EngFavR.setSelected(true);
        EngFavR.setText("Eng");

        buttonGroup3.add(vieFavR);
        vieFavR.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        vieFavR.setText("Vie");

        favList.setFont(new java.awt.Font("SVN-Aguda", 0, 14)); // NOI18N
        jScrollPane3.setViewportView(favList);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(AZBtn)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vieFavR)
                            .addComponent(ZABtn))
                        .addGap(18, 64, Short.MAX_VALUE)
                        .addComponent(removeBtn))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(EngFavR)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeBtn)
                    .addComponent(EngFavR)
                    .addComponent(vieFavR))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ZABtn)
                    .addComponent(AZBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Favorites", jPanel8);

        history.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Word", "Search count"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(history);

        startDate.setForeground(new java.awt.Color(0, 0, 0));
        startDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        startDate.setText("03/04/2024");
        startDate.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N

        endDate.setForeground(new java.awt.Color(0, 0, 0));
        endDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        endDate.setText("03/04/2024");
        endDate.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N

        startOn.setBackground(new java.awt.Color(89, 50, 209));
        startOn.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        startOn.setForeground(new java.awt.Color(89, 50, 213));
        startOn.setText("From date:");

        endOn.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        endOn.setForeground(new java.awt.Color(89, 50, 213));
        endOn.setText("To date:");

        searchHistory.setBackground(new java.awt.Color(89, 50, 219));
        searchHistory.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        searchHistory.setForeground(new java.awt.Color(255, 255, 255));
        searchHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startOn))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endOn)
                            .addComponent(endDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startOn, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(endOn, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(searchHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Statistics", jPanel9);

        searchResult.setFont(new java.awt.Font("SVN-Aguda", 0, 15)); // NOI18N
        searchResult.setForeground(new java.awt.Color(89, 50, 213));
        searchResult.setText("Search result:");
        searchResult.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(searchResult, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(searchResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName("Meaning");

        jMenuBar1.setBackground(new java.awt.Color(89, 52, 213));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jMenuBar1.setForeground(new java.awt.Color(153, 153, 255));

        settingOption.setToolTipText("");
        settingOption.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        settingOption.setLabel("Settings");

        language.setText("Language");
        language.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N

        buttonGroup4.add(EngLang);
        EngLang.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        EngLang.setSelected(true);
        EngLang.setText("Eng");
        language.add(EngLang);

        buttonGroup4.add(vieLang);
        vieLang.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        vieLang.setText("Vie");
        language.add(vieLang);

        settingOption.add(language);

        jMenuBar1.add(settingOption);

        aboutMe.setText("About me");
        aboutMe.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N

        Information.setFont(new java.awt.Font("SVN-Aguda", 0, 13)); // NOI18N
        Information.setText("Information");
        aboutMe.add(Information);

        jMenuBar1.add(aboutMe);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void addFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFavActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addFavActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeBtnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.setVisible(false);
        this.myDictionary.exportEngVieWords();
        this.myDictionary.exportEngFavWords();
        this.myDictionary.exportVieEngWords();
        this.myDictionary.exportVieFavWords();
        this.myDictionary.exportHistory();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyDictionaryGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyDictionaryGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyDictionaryGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyDictionaryGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyDictionaryGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton AZBtn;
    private javax.swing.JRadioButton EnVieR;
    private javax.swing.JRadioButton EngFavR;
    private javax.swing.JRadioButtonMenuItem EngLang;
    private javax.swing.JMenuItem Information;
    private javax.swing.JRadioButton VieEnR;
    private javax.swing.JRadioButton ZABtn;
    private javax.swing.JMenu aboutMe;
    private javax.swing.JButton addFav;
    private javax.swing.JButton addWordBtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JFormattedTextField endDate;
    private javax.swing.JLabel endOn;
    private javax.swing.JList<String> favList;
    private javax.swing.JTable history;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JMenu language;
    private javax.swing.JPanel meaningTab;
    private javax.swing.JTextArea meaningText;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton searchHistory;
    private javax.swing.JLabel searchResult;
    private javax.swing.JMenu settingOption;
    private javax.swing.JFormattedTextField startDate;
    private javax.swing.JLabel startOn;
    private javax.swing.JLabel tools;
    private javax.swing.JRadioButton vieFavR;
    private javax.swing.JRadioButtonMenuItem vieLang;
    private javax.swing.JList<String> wordList;
    // End of variables declaration//GEN-END:variables
}
