package com.tickercash.window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.AsynchronousTextGUIThread;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Panels;
import com.googlecode.lanterna.gui2.SeparateTextGUIThread;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.table.DefaultTableRenderer;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableCellBorderStyle;
import com.googlecode.lanterna.gui2.table.TableModel;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.tickercash.enumz.BookOptions;
import com.tickercash.enumz.Exchange;
import com.tickercash.enumz.Symbol;
import com.tickercash.enumz.Tip;

public class TerminalStarter {
	
    public static void main(String[] args) throws IOException, InterruptedException {
        new TerminalStarter().run(args);
    }
    
    void run(String[] args) throws IOException, InterruptedException {
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        MultiWindowTextGUI textGUI = createTextGUI(screen);
        
        textGUI.setTheme(LanternaThemes.getRegisteredTheme("businessmachine"));
        textGUI.setBlockingIO(false);
        textGUI.setEOFWhenNoWindows(true);
        textGUI.isEOFWhenNoWindows();   //No meaning, just to silence IntelliJ:s "is never used" alert

        try {
            init(textGUI);
            AsynchronousTextGUIThread guiThread = (AsynchronousTextGUIThread)textGUI.getGUIThread();
            guiThread.start();
            afterGUIThreadStarted(textGUI);
            guiThread.waitForStop();
        }
        finally {
            screen.stopScreen();
        }
    }
    
    public void afterGUIThreadStarted(WindowBasedTextGUI textGUI) {
        // By default do nothing
    }
    
    protected MultiWindowTextGUI createTextGUI(Screen screen) {
        return new MultiWindowTextGUI(new SeparateTextGUIThread.Factory(), screen);
    }

    public void init(final WindowBasedTextGUI textGUI) {
        final BasicWindow window = new BasicWindow("Tape Reader");
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        
        List<String> columns = Arrays.asList(BookOptions.values()).stream().map(e -> e.toString()).collect(Collectors.toList());
        final Table<String> table = new Table<String>(columns.toArray(new String[BookOptions.values().length]));
        final TableModel<String> model = table.getTableModel();
        model.addRow(Symbol.BITCOIN.toString(), Exchange.GDAX.toString(), Exchange.CMC.toString(), Tip.HIGH_LOW.toString());

        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        buttonPanel.addComponent(new Button("Add...", new Runnable() {
            @Override
            public void run() {
                new ActionListDialogBuilder()
                        .setTitle("Add to table")
                        .addAction("Row", new Runnable() {
                            @Override
                            public void run() {
                                List<String> labels = new ArrayList<String>();
                                for(int i = 0; i < model.getColumnCount(); i++) {
                                    labels.add("Row" + (model.getRowCount() + 1));
                                }
                                model.addRow(labels.toArray(new String[labels.size()]));
                                table.invalidate();
                            }
                        })
                        .addAction("5 Rows", new Runnable() {
                            @Override
                            public void run() {
                                for(int row = 0; row < 5; row++) {
                                    List<String> labels = new ArrayList<String>();
                                    for(int i = 0; i < model.getColumnCount(); i++) {
                                        labels.add("Row" + (model.getRowCount() + 1));
                                    }
                                    model.addRow(labels.toArray(new String[labels.size()]));
                                }
                                table.invalidate();
                            }
                        })
                        .build()
                        .showDialog(textGUI);
            }
        }));
        buttonPanel.addComponent(new Button("Modify...", new Runnable() {
            @Override
            public void run() {
                onModify(textGUI, table);
            }
        }));
        buttonPanel.addComponent(new Button("Remove...", new Runnable() {
            @Override
            public void run() {
                new ActionListDialogBuilder()
                        .setTitle("Remove from table")
                        .addAction("Row", new Runnable() {
                            @Override
                            public void run() {
                                String numberAsText = askForANumber(textGUI, "Enter row # to remove (0-" + (model.getRowCount()-1) + ")");
                                if(numberAsText != null) {
                                    model.removeRow(Integer.parseInt(numberAsText));
                                }
                            }
                        })
                        .addAction("Column", new Runnable() {
                            @Override
                            public void run() {
                                String numberAsText = askForANumber(textGUI, "Enter column # to remove (0-" + (model.getColumnCount()-1) + ")");
                                if(numberAsText != null) {
                                    model.removeColumn(Integer.parseInt(numberAsText));
                                }
                            }
                        })
                        .build()
                        .showDialog(textGUI);
            }
        }));
        buttonPanel.addComponent(new Button("Run...", new Runnable() {
            @Override
            public void run() {
                window.close();
            }
        }));
        
        
        buttonPanel.addComponent(new Button("Close", new Runnable() {
            @Override
            public void run() {
                window.close();
            }
        }));
        window.setComponent(Panels.vertical(
                table.withBorder(Borders.singleLineBevel(" ")),
                buttonPanel));
        textGUI.addWindow(window);
    }

    private void onModify(WindowBasedTextGUI textGUI, Table<String> table) {
        String[] dialogChoices = new String[] {
                "Change table content",
                "Change table style",
                "Change view size",
                "Force re-calculate/re-draw"
        };
        String choice = chooseAString(textGUI, "Modify what?", dialogChoices);
        if(choice == null) {
        }
        else if(choice.equals(dialogChoices[0])) {
            onModifyContent(textGUI, table);
        }
        else if(choice.equals(dialogChoices[1])) {
            onModifyStyle(textGUI, table);
        }
        else if(choice.equals(dialogChoices[2])) {
            onModifyViewSize(textGUI, table);
        }
        else if(choice.equals(dialogChoices[3])) {
            table.invalidate();
        }
    }

    private void onModifyContent(WindowBasedTextGUI textGUI, Table<String> table) {
        TableModel<String> model = table.getTableModel();
        String columnIndexAsText = askForANumber(textGUI, "Enter column # to modify (0-" + (model.getColumnCount() - 1) + ")");
        if(columnIndexAsText == null) {
            return;
        }
        String rowIndexAsText = askForANumber(textGUI, "Enter row # to modify (0-" + (model.getRowCount() - 1) + ")");
        if(rowIndexAsText == null) {
            return;
        }
        String newLabel = askForAString(textGUI, "Enter new label for the table cell at row " + rowIndexAsText + " column " + columnIndexAsText);
        if(newLabel != null) {
            model.setCell(Integer.parseInt(columnIndexAsText), Integer.parseInt(rowIndexAsText), newLabel);
        }
    }

    private void onModifyStyle(WindowBasedTextGUI textGUI, Table<String> table) {
        String[] dialogChoices = new String[] {
                "Header border style (vertical)",
                "Header border style (horizontal)",
                "Cell border style (vertical)",
                "Cell border style (horizontal)",
                "Toggle cell selection"
        };
        String choice = chooseAString(textGUI, "Which style do you want to change?", dialogChoices);
        DefaultTableRenderer<String> renderer = (DefaultTableRenderer<String>) table.getRenderer();
        if(choice == null) {
            return;
        }
        else if(choice.equals(dialogChoices[4])) {
            table.setCellSelection(!table.isCellSelection());
        }
        else {
            TableCellBorderStyle newStyle = new ListSelectDialogBuilder<TableCellBorderStyle>()
                    .setTitle("Choose a new style")
                    .addListItems(TableCellBorderStyle.values())
                    .build()
                    .showDialog(textGUI);
            if(newStyle != null) {
                if(choice.equals(dialogChoices[0])) {
                    renderer.setHeaderVerticalBorderStyle(newStyle);
                }
                else if(choice.equals(dialogChoices[1])) {
                    renderer.setHeaderHorizontalBorderStyle(newStyle);
                }
                else if(choice.equals(dialogChoices[2])) {
                    renderer.setCellVerticalBorderStyle(newStyle);
                }
                else if(choice.equals(dialogChoices[3])) {
                    renderer.setCellHorizontalBorderStyle(newStyle);
                }
            }
        }
        table.invalidate();
    }

    private void onModifyViewSize(WindowBasedTextGUI textGUI, Table<String> table) {
        String verticalViewSize = askForANumber(textGUI, "Enter number of rows to display at once (0 = all)");
        if(verticalViewSize == null) {
            return;
        }
        table.setVisibleRows(Integer.parseInt(verticalViewSize));
        String horizontalViewSize = askForANumber(textGUI, "Enter number of columns to display at once (0 = all)");
        if(horizontalViewSize == null) {
            return;
        }
        table.setVisibleColumns(Integer.parseInt(horizontalViewSize));
    }

    private String chooseAString(WindowBasedTextGUI textGUI, String title, String... items) {
        return new ListSelectDialogBuilder<String>()
                .setTitle(title)
                .addListItems(items)
                .build()
                .showDialog(textGUI);
    }

    private String askForAString(WindowBasedTextGUI textGUI, String title) {
        return new TextInputDialogBuilder()
                .setTitle(title)
                .build()
                .showDialog(textGUI);
    }

    private String askForANumber(WindowBasedTextGUI textGUI, String title) {
        return askForANumber(textGUI, title, "");
    }

    private String askForANumber(WindowBasedTextGUI textGUI, String title, String initialNumber) {
        return new TextInputDialogBuilder()
                .setTitle(title)
                .setInitialContent(initialNumber)
                .setValidationPattern(Pattern.compile("[0-9]+"), "Not a number")
                .build()
                .showDialog(textGUI);
    }

}
