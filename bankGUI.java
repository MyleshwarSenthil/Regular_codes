import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

// ══════════════════════════════════════════════════════════
//  DATA MODELS
// ══════════════════════════════════════════════════════════

class TxRecord {
    final String type, timestamp;
    final double amount, balanceAfter;
    TxRecord(String type, double amount, double balanceAfter) {
        this.type        = type;
        this.amount      = amount;
        this.balanceAfter= balanceAfter;
        this.timestamp   = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy  HH:mm"));
    }
}

class Account {
    static int counter = 1000;
    final String accNo, holder, type;
    double balance;
    final double overdraft;
    final List<TxRecord> history = new ArrayList<>();

    Account(String holder, String type, double initial, double overdraft) {
        this.accNo    = "ACC" + (++counter);
        this.holder   = holder;
        this.type     = type;
        this.balance  = initial;
        this.overdraft= overdraft;
        if (initial > 0) history.add(new TxRecord("OPEN", initial, balance));
    }

    boolean deposit(double amt) {
        if (amt <= 0) return false;
        balance += amt;
        history.add(new TxRecord("DEPOSIT", amt, balance));
        return true;
    }

    boolean withdraw(double amt) {
        if (amt <= 0) return false;
        if (amt > balance + overdraft) return false;
        balance -= amt;
        history.add(new TxRecord("WITHDRAW", -amt, balance));
        return true;
    }

    void applyInterest() {
        if (!type.equals("SAVINGS")) return;
        double interest = balance * 0.04;
        balance += interest;
        history.add(new TxRecord("INTEREST", interest, balance));
    }
}

class Bank {
    final Map<String, Account> accounts = new LinkedHashMap<>();

    Account createAccount(String holder, String type, double initial, double overdraft) {
        Account a = new Account(holder, type, initial, overdraft);
        accounts.put(a.accNo, a);
        return a;
    }

    boolean transfer(String from, String to, double amt) {
        Account f = accounts.get(from), t = accounts.get(to);
        if (f == null || t == null) return false;
        if (!f.withdraw(amt)) return false;
        t.deposit(amt);
        // fix: record as transfer
        f.history.get(f.history.size()-1);
        return true;
    }
}

// ══════════════════════════════════════════════════════════
//  CUSTOM UI COMPONENTS
// ══════════════════════════════════════════════════════════

class Theme {
    static final Color BG         = new Color(10,  12,  20);
    static final Color SURFACE    = new Color(18,  22,  36);
    static final Color CARD       = new Color(24,  30,  50);
    static final Color BORDER     = new Color(45,  55,  90);
    static final Color GOLD       = new Color(255, 196,  60);
    static final Color GOLD_DIM   = new Color(180, 135,  40);
    static final Color GREEN      = new Color( 50, 210, 130);
    static final Color RED        = new Color(255,  80,  80);
    static final Color TEXT       = new Color(220, 225, 240);
    static final Color TEXT_DIM   = new Color(120, 130, 160);
    static final Color HIGHLIGHT  = new Color(60,  80, 140);

    static final Font  TITLE  = new Font("Serif", Font.BOLD, 26);
    static final Font  LABEL  = new Font("Monospaced", Font.BOLD, 12);
    static final Font  BODY   = new Font("Monospaced", Font.PLAIN, 12);
    static final Font  SMALL  = new Font("Monospaced", Font.PLAIN, 11);
    static final Font  MONO   = new Font("Monospaced", Font.PLAIN, 13);
}

class GoldButton extends JButton {
    private boolean hovered = false;
    GoldButton(String text) {
        super(text);
        setFont(Theme.LABEL);
        setForeground(Theme.BG);
        setBackground(Theme.GOLD);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(160, 38));
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
            public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
        });
    }
    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color c = hovered ? Theme.GOLD : Theme.GOLD_DIM;
        g2.setColor(c);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2.setColor(Theme.BG);
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth()  - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);
        g2.dispose();
    }
}

class DarkField extends JTextField {
    DarkField(int cols) {
        super(cols);
        setBackground(Theme.SURFACE);
        setForeground(Theme.TEXT);
        setCaretColor(Theme.GOLD);
        setFont(Theme.MONO);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }
}

class DarkCombo extends JComboBox<String> {
    DarkCombo(String[] items) {
        super(items);
        setBackground(Theme.SURFACE);
        setForeground(Theme.TEXT);
        setFont(Theme.MONO);
        setBorder(BorderFactory.createLineBorder(Theme.BORDER, 1));
        setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> l, Object v,
                    int i, boolean sel, boolean foc) {
                super.getListCellRendererComponent(l, v, i, sel, foc);
                setBackground(sel ? Theme.HIGHLIGHT : Theme.SURFACE);
                setForeground(Theme.TEXT);
                setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
                return this;
            }
        });
    }
}

class SectionCard extends JPanel {
    SectionCard(String title) {
        setLayout(new BorderLayout(0, 12));
        setBackground(Theme.CARD);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));
        if (title != null && !title.isEmpty()) {
            JLabel lbl = new JLabel(title);
            lbl.setFont(Theme.LABEL);
            lbl.setForeground(Theme.GOLD);
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
            add(lbl, BorderLayout.NORTH);
        }
    }
}

// ══════════════════════════════════════════════════════════
//  MAIN APPLICATION WINDOW
// ══════════════════════════════════════════════════════════

public class bankGUI extends JFrame {

    private final Bank bank = new Bank();

    // sidebar nav
    private final String[] NAV = {"Dashboard","Create Account","Deposit","Withdraw","Transfer","Statement","All Accounts"};
    private final JPanel   contentPanel = new JPanel(new CardLayout());
    private final JLabel   statusBar    = new JLabel("  Ready");

    // panels
    private DashboardPanel   dashPanel;
    private AllAccountsPanel allPanel;
    private DepositPanel     depositPanel;
    private WithdrawPanel    withdrawPanel;
    private TransferPanel    transferPanel;
    private StatementPanel   statementPanel;

    public bankGUI() {
        super("JavaBank — Account Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG);

        seedDemoData();
        buildUI();
        setVisible(true);
    }

    private void seedDemoData() {
        bank.createAccount("Alice Kumar",  "SAVINGS", 50000, 0);
        bank.createAccount("Bob Sharma",   "CURRENT", 30000, 10000);
        bank.createAccount("Carol Nair",   "SAVINGS", 20000, 0);
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        // ── Header ────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.SURFACE);
        header.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Theme.BORDER));
        header.setPreferredSize(new Dimension(0, 56));

        JLabel logo = new JLabel("  ◈  JavaBank");
        logo.setFont(Theme.TITLE);
        logo.setForeground(Theme.GOLD);
        header.add(logo, BorderLayout.WEST);

        JLabel tagline = new JLabel("Account Management System  ");
        tagline.setFont(Theme.SMALL);
        tagline.setForeground(Theme.TEXT_DIM);
        header.add(tagline, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ── Sidebar ───────────────────────────
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Theme.SURFACE);
        sidebar.setPreferredSize(new Dimension(190, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Theme.BORDER));

        sidebar.add(Box.createVerticalStrut(16));
        ButtonGroup bg = new ButtonGroup();
        for (String nav : NAV) {
            JToggleButton btn = makeNavButton(nav);
            bg.add(btn);
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(4));
            btn.addActionListener(e -> showCard(nav));
        }
        sidebar.add(Box.createVerticalGlue());
        add(sidebar, BorderLayout.WEST);

        // ── Content Panels ────────────────────
        dashPanel = new DashboardPanel();
        allPanel  = new AllAccountsPanel();

        contentPanel.add(dashPanel,              "Dashboard");
        contentPanel.add(new CreateAccountPanel(),"Create Account");
        depositPanel  = new DepositPanel();
        withdrawPanel = new WithdrawPanel();
        transferPanel = new TransferPanel();
        statementPanel= new StatementPanel();
        contentPanel.add(depositPanel,             "Deposit");
        contentPanel.add(withdrawPanel,            "Withdraw");
        contentPanel.add(transferPanel,            "Transfer");
        contentPanel.add(statementPanel,           "Statement");
        contentPanel.add(allPanel,               "All Accounts");
        contentPanel.setBackground(Theme.BG);
        add(contentPanel, BorderLayout.CENTER);

        // ── Status Bar ────────────────────────
        statusBar.setFont(Theme.SMALL);
        statusBar.setForeground(Theme.TEXT_DIM);
        statusBar.setBackground(Theme.SURFACE);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(0, 26));
        statusBar.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Theme.BORDER));
        add(statusBar, BorderLayout.SOUTH);

        // default selection
        showCard("Dashboard");
    }

    private JToggleButton makeNavButton(String label) {
        JToggleButton btn = new JToggleButton(label) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                if (isSelected()) {
                    g2.setColor(Theme.HIGHLIGHT);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(Theme.GOLD);
                    g2.fillRect(0, 0, 3, getHeight());
                }
                g2.setColor(isSelected() ? Theme.GOLD : (getModel().isRollover() ? Theme.TEXT : Theme.TEXT_DIM));
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(), 20, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setFont(Theme.LABEL);
        btn.setMaximumSize(new Dimension(190, 40));
        btn.setPreferredSize(new Dimension(190, 40));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void showCard(String name) {
        if (name.equals("Dashboard"))    dashPanel.refresh();
        if (name.equals("All Accounts")) allPanel.refresh();
        if (name.equals("Deposit"))      depositPanel.refresh();
        if (name.equals("Withdraw"))     withdrawPanel.refresh();
        if (name.equals("Transfer"))     transferPanel.refresh();
        if (name.equals("Statement"))    statementPanel.refresh();
    ((CardLayout) contentPanel.getLayout()).show(contentPanel, name);
}

    private void setStatus(String msg, boolean ok) {
        statusBar.setForeground(ok ? Theme.GREEN : Theme.RED);
        statusBar.setText("  " + msg);
    }

    // ── Helpers ───────────────────────────────
    private JComboBox<String> makeAccountCombo() {
        String[] items = bank.accounts.values().stream()
                .map(a -> a.accNo + " — " + a.holder + " (" + a.type + ")")
                .toArray(String[]::new);
        return new DarkCombo(items.length > 0 ? items : new String[]{"(no accounts)"});
    }

    private String extractAccNo(JComboBox<String> combo) {
        Object sel = combo.getSelectedItem();
        if (sel == null) return null;
        return sel.toString().split(" — ")[0];
    }

    // ══════════════════════════════════════════
    //  PANELS
    // ══════════════════════════════════════════

    // ── Dashboard ─────────────────────────────
    class DashboardPanel extends JPanel {
        private final JLabel totalLabel   = makeStatLabel("₹0");
        private final JLabel countLabel   = makeStatLabel("0");
        private final JLabel savingsLabel = makeStatLabel("0");
        private final JLabel currentLabel = makeStatLabel("0");
        private final DefaultTableModel recentModel = new DefaultTableModel(
                new String[]{"Account","Holder","Type","Balance"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        DashboardPanel() {
            setBackground(Theme.BG);
            setLayout(new BorderLayout(16, 16));
            setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

            JLabel title = new JLabel("Dashboard");
            title.setFont(new Font("Serif", Font.BOLD, 22));
            title.setForeground(Theme.GOLD);
            add(title, BorderLayout.NORTH);

            // stat cards
            JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
            stats.setOpaque(false);
            stats.add(statCard("Total Assets",     totalLabel,   "₹"));
            stats.add(statCard("Total Accounts",   countLabel,   "#"));
            stats.add(statCard("Savings Accounts", savingsLabel, "S"));
            stats.add(statCard("Current Accounts", currentLabel, "C"));

            // recent table
            JTable table = new JTable(recentModel);
            styleTable(table);
            JScrollPane scroll = new JScrollPane(table);
            styleScroll(scroll);

            SectionCard tableCard = new SectionCard("All Accounts Overview");
            tableCard.add(scroll, BorderLayout.CENTER);

            JPanel center = new JPanel(new BorderLayout(0, 16));
            center.setOpaque(false);
            center.add(stats, BorderLayout.NORTH);
            center.add(tableCard, BorderLayout.CENTER);
            add(center, BorderLayout.CENTER);

            refresh();
        }

        private JPanel statCard(String label, JLabel valueLabel, String icon) {
            JPanel p = new JPanel(new BorderLayout(0, 6));
            p.setBackground(Theme.CARD);
            p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
            ));
            JLabel ico = new JLabel(icon);
            ico.setFont(new Font("Serif", Font.BOLD, 28));
            ico.setForeground(Theme.GOLD);
            JLabel lbl = new JLabel(label);
            lbl.setFont(Theme.SMALL);
            lbl.setForeground(Theme.TEXT_DIM);
            p.add(ico,        BorderLayout.NORTH);
            p.add(valueLabel, BorderLayout.CENTER);
            p.add(lbl,        BorderLayout.SOUTH);
            return p;
        }

        private JLabel makeStatLabel(String text) {
            JLabel l = new JLabel(text);
            l.setFont(new Font("Monospaced", Font.BOLD, 22));
            l.setForeground(Theme.TEXT);
            return l;
        }

        void refresh() {
            double total = bank.accounts.values().stream().mapToDouble(a -> a.balance).sum();
            long savings  = bank.accounts.values().stream().filter(a -> a.type.equals("SAVINGS")).count();
            long current  = bank.accounts.values().stream().filter(a -> a.type.equals("CURRENT")).count();
            totalLabel.setText("₹" + String.format("%,.0f", total));
            countLabel.setText(String.valueOf(bank.accounts.size()));
            savingsLabel.setText(String.valueOf(savings));
            currentLabel.setText(String.valueOf(current));

            recentModel.setRowCount(0);
            for (Account a : bank.accounts.values()) {
                recentModel.addRow(new Object[]{
                    a.accNo, a.holder, a.type,
                    String.format("₹%,.2f", a.balance)
                });
            }
        }
    }

    // ── Create Account ────────────────────────
    class CreateAccountPanel extends JPanel {
        CreateAccountPanel() {
            setBackground(Theme.BG);
            setLayout(new BorderLayout(0, 16));
            setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

            JLabel title = new JLabel("Create New Account");
            title.setFont(new Font("Serif", Font.BOLD, 22));
            title.setForeground(Theme.GOLD);
            add(title, BorderLayout.NORTH);

            SectionCard card = new SectionCard("Account Details");
            JPanel form = new JPanel(new GridBagLayout());
            form.setOpaque(false);
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(8, 6, 8, 6);
            g.anchor = GridBagConstraints.WEST;

            DarkField nameField      = new DarkField(22);
            DarkField initialField   = new DarkField(22);
            DarkField overdraftField = new DarkField(22);
            DarkCombo typeCombo      = new DarkCombo(new String[]{"SAVINGS","CURRENT"});
            overdraftField.setText("0");
            overdraftField.setEnabled(false);

            typeCombo.addActionListener(e -> {
                overdraftField.setEnabled("CURRENT".equals(typeCombo.getSelectedItem()));
            });

            addRow(form, g, 0, "Holder Name:",      nameField);
            addRow(form, g, 1, "Account Type:",     typeCombo);
            addRow(form, g, 2, "Initial Deposit (₹):", initialField);
            addRow(form, g, 3, "Overdraft Limit (₹):", overdraftField);

            GoldButton btn = new GoldButton("Create Account");
            btn.addActionListener(e -> {
                String name = nameField.getText().trim();
                if (name.isEmpty()) { setStatus("Enter holder name", false); return; }
                try {
                    double init = Double.parseDouble(initialField.getText().trim());
                    double ovrd = Double.parseDouble(overdraftField.getText().trim());
                    String type = (String) typeCombo.getSelectedItem();
                    Account a = bank.createAccount(name, type, init, ovrd);
                    setStatus("Account " + a.accNo + " created for " + name, true);
                    nameField.setText(""); initialField.setText(""); overdraftField.setText("0");
                    dashPanel.refresh(); allPanel.refresh();
                } catch (NumberFormatException ex) {
                    setStatus("Invalid amount entered", false);
                }
            });

            g.gridx = 1; g.gridy = 5; form.add(btn, g);
            card.add(form, BorderLayout.CENTER);
            add(card, BorderLayout.CENTER);
        }
    }

    // ── Deposit ───────────────────────────────
    class DepositPanel extends JPanel {
        private JComboBox<String> accCombo;

        DepositPanel() {
            setBackground(Theme.BG);
            setLayout(new BorderLayout(0, 16));
            setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

            JLabel title = new JLabel("Deposit Funds");
            title.setFont(new Font("Serif", Font.BOLD, 22));
            title.setForeground(Theme.GOLD);
            add(title, BorderLayout.NORTH);

            SectionCard card = new SectionCard("Deposit Details");
            JPanel form = new JPanel(new GridBagLayout());
            form.setOpaque(false);
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(10, 6, 10, 6);
            g.anchor = GridBagConstraints.WEST;

            accCombo = makeAccountCombo();
            DarkField amtField = new DarkField(20);

            addRow(form, g, 0, "Select Account:", accCombo);
            addRow(form, g, 1, "Amount (₹):",     amtField);

            JLabel balLabel = new JLabel("Balance: —");
            balLabel.setFont(Theme.MONO); balLabel.setForeground(Theme.GREEN);
            g.gridx=1; g.gridy=2; form.add(balLabel, g);

            GoldButton btn = new GoldButton("Deposit");
            btn.addActionListener(e -> {
                String accNo = extractAccNo(accCombo);
                Account a = bank.accounts.get(accNo);
                if (a == null) { setStatus("Select a valid account", false); return; }
                try {
                    double amt = Double.parseDouble(amtField.getText().trim());
                    if (a.deposit(amt)) {
                        balLabel.setText("Balance: ₹" + String.format("%,.2f", a.balance));
                        setStatus("Deposited ₹" + String.format("%,.2f", amt) + " to " + accNo, true);
                        amtField.setText("");
                        dashPanel.refresh();
                    } else { setStatus("Invalid amount", false); }
                } catch (NumberFormatException ex) { setStatus("Enter a valid amount", false); }
            });
            g.gridx=1; g.gridy=3; form.add(btn, g);
            card.add(form, BorderLayout.CENTER);
            add(card, BorderLayout.CENTER);
        }
            void refresh() {
            accCombo.setModel(new DefaultComboBoxModel<>(
            bank.accounts.values().stream()
            .map(a -> a.accNo + " — " + a.holder + " (" + a.type + ")")
            .toArray(String[]::new)));
        }
    }

    // ── Withdraw ──────────────────────────────
    class WithdrawPanel extends JPanel {
        private JComboBox<String> accCombo;
        WithdrawPanel() {
            setBackground(Theme.BG);
            setLayout(new BorderLayout(0, 16));
            setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

            JLabel title = new JLabel("Withdraw Funds");
            title.setFont(new Font("Serif", Font.BOLD, 22));
            title.setForeground(Theme.GOLD);
            add(title, BorderLayout.NORTH);

            SectionCard card = new SectionCard("Withdrawal Details");
            JPanel form = new JPanel(new GridBagLayout());
            form.setOpaque(false);
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(10, 6, 10, 6);
            g.anchor = GridBagConstraints.WEST;

            accCombo = makeAccountCombo();
            DarkField amtField = new DarkField(20);
            JLabel balLabel = new JLabel("Balance: —");
            balLabel.setFont(Theme.MONO); balLabel.setForeground(Theme.RED);

            addRow(form, g, 0, "Select Account:", accCombo);
            addRow(form, g, 1, "Amount (₹):",     amtField);
            g.gridx=1; g.gridy=2; form.add(balLabel, g);

            GoldButton btn = new GoldButton("Withdraw");
            btn.addActionListener(e -> {
                String accNo = extractAccNo(accCombo);
                Account a = bank.accounts.get(accNo);
                if (a == null) { setStatus("Select a valid account", false); return; }
                try {
                    double amt = Double.parseDouble(amtField.getText().trim());
                    if (a.withdraw(amt)) {
                        balLabel.setText("Balance: ₹" + String.format("%,.2f", a.balance));
                        setStatus("Withdrew ₹" + String.format("%,.2f", amt) + " from " + accNo, true);
                        amtField.setText(""); dashPanel.refresh();
                    } else { setStatus("Insufficient funds or invalid amount", false); }
                } catch (NumberFormatException ex) { setStatus("Enter a valid amount", false); }
            });
            g.gridx=1; g.gridy=3; form.add(btn, g);
            card.add(form, BorderLayout.CENTER);
            add(card, BorderLayout.CENTER);
        }
         void refresh() {
            accCombo.setModel(new DefaultComboBoxModel<>(
                bank.accounts.values().stream()
                    .map(a -> a.accNo + " — " + a.holder + " (" + a.type + ")")
                    .toArray(String[]::new)));
        }
    }

    // ── Transfer ──────────────────────────────
    class TransferPanel extends JPanel {
        private JComboBox<String> fromCombo;         // ← add fields
        private JComboBox<String> toCombo;

        TransferPanel() {
            setBackground(Theme.BG);
            setLayout(new BorderLayout(0, 16));
            setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

            JLabel title = new JLabel("Transfer Funds");
            title.setFont(new Font("Serif", Font.BOLD, 22));
            title.setForeground(Theme.GOLD);
            add(title, BorderLayout.NORTH);

            SectionCard card = new SectionCard("Transfer Details");
            JPanel form = new JPanel(new GridBagLayout());
            form.setOpaque(false);
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(10, 6, 10, 6);
            g.anchor = GridBagConstraints.WEST;

            fromCombo = makeAccountCombo();
            toCombo   = makeAccountCombo();
            DarkField amtField = new DarkField(20);

            addRow(form, g, 0, "From Account:", fromCombo);
            addRow(form, g, 1, "To Account:",   toCombo);
            addRow(form, g, 2, "Amount (₹):",   amtField);

            GoldButton btn = new GoldButton("Transfer");
            btn.addActionListener(e -> {
                String from = extractAccNo(fromCombo);
                String to   = extractAccNo(toCombo);
                if (from == null || to == null || from.equals(to)) {
                    setStatus("Select two different accounts", false); return;
                }
                try {
                    double amt = Double.parseDouble(amtField.getText().trim());
                    if (bank.transfer(from, to, amt)) {
                        setStatus("Transferred ₹" + String.format("%,.2f", amt) + " (" + from + " → " + to + ")", true);
                        amtField.setText(""); dashPanel.refresh();
                    } else { setStatus("Transfer failed — insufficient funds", false); }
                } catch (NumberFormatException ex) { setStatus("Enter a valid amount", false); }
            });
            g.gridx=1; g.gridy=3; form.add(btn, g);
            card.add(form, BorderLayout.CENTER);
            add(card, BorderLayout.CENTER);
        }
            void refresh() {
            String[] items = bank.accounts.values().stream()
            .map(a -> a.accNo + " — " + a.holder + " (" + a.type + ")")
            .toArray(String[]::new);
            fromCombo.setModel(new DefaultComboBoxModel<>(items));
            toCombo.setModel(new DefaultComboBoxModel<>(items));
        }
    }

    // ── Statement ─────────────────────────────
    class StatementPanel extends JPanel {
        private final DefaultTableModel model = new DefaultTableModel(
                new String[]{"Timestamp","Type","Amount (₹)","Balance (₹)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        private final JLabel balLabel = new JLabel("—");
        private JComboBox<String> accCombo; 

        StatementPanel() {
            setBackground(Theme.BG);
            setLayout(new BorderLayout(0, 16));
            setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

            JLabel title = new JLabel("Account Statement");
            title.setFont(new Font("Serif", Font.BOLD, 22));
            title.setForeground(Theme.GOLD);
            add(title, BorderLayout.NORTH);

            accCombo = makeAccountCombo();
            GoldButton btn = new GoldButton("Load Statement");
            balLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
            balLabel.setForeground(Theme.GREEN);

            JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            top.setOpaque(false);
            top.add(label("Account:"));
            top.add(accCombo);
            top.add(btn);
            top.add(label("  Balance:"));
            top.add(balLabel);

            JTable table = new JTable(model);
            styleTable(table);
            // colour rows
            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable t, Object val,
                        boolean sel, boolean foc, int row, int col) {
                    super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                    setBackground(sel ? Theme.HIGHLIGHT : (row%2==0 ? Theme.CARD : Theme.SURFACE));
                    String type = (String) t.getModel().getValueAt(row, 1);
                    if (col == 2) {
                        setForeground(type.equals("WITHDRAW") ? Theme.RED : Theme.GREEN);
                    } else {
                        setForeground(Theme.TEXT);
                    }
                    setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                    return this;
                }
            });

            JScrollPane scroll = new JScrollPane(table);
            styleScroll(scroll);

            SectionCard card = new SectionCard("Transaction History");
            card.add(top, BorderLayout.NORTH);
            card.add(scroll, BorderLayout.CENTER);
            add(card, BorderLayout.CENTER);

            btn.addActionListener(e -> {
                String accNo = extractAccNo(accCombo);
                Account a = bank.accounts.get(accNo);
                if (a == null) { setStatus("Select account", false); return; }
                model.setRowCount(0);
                for (TxRecord tx : a.history) {
                    model.addRow(new Object[]{
                        tx.timestamp, tx.type,
                        String.format("%+,.2f", tx.amount),
                        String.format("%,.2f", tx.balanceAfter)
                    });
                }
                balLabel.setText("₹" + String.format("%,.2f", a.balance));
                setStatus("Statement loaded for " + accNo, true);
            });

            // interest button
            GoldButton interestBtn = new GoldButton("Apply Interest");
            interestBtn.addActionListener(e -> {
                String accNo = extractAccNo(accCombo);
                Account a = bank.accounts.get(accNo);
                if (a == null) return;
                if (!a.type.equals("SAVINGS")) { setStatus("Interest only for Savings accounts", false); return; }
                a.applyInterest();
                balLabel.setText("₹" + String.format("%,.2f", a.balance));
                model.setRowCount(0);
                for (TxRecord tx : a.history) {
                    model.addRow(new Object[]{tx.timestamp, tx.type,
                        String.format("%+,.2f", tx.amount),
                        String.format("%,.2f", tx.balanceAfter)});
                }
                setStatus("Interest applied to " + accNo, true);
                dashPanel.refresh();
            });
            top.add(interestBtn);
        }

        private JLabel label(String text) {
            JLabel l = new JLabel(text);
            l.setFont(Theme.LABEL);
            l.setForeground(Theme.TEXT_DIM);
            return l;
        }
            void refresh() {
            accCombo.setModel(new DefaultComboBoxModel<>(
            bank.accounts.values().stream()
            .map(a -> a.accNo + " — " + a.holder + " (" + a.type + ")")
            .toArray(String[]::new)));
        }
    }

    // ── All Accounts ──────────────────────────
    class AllAccountsPanel extends JPanel {
        private final DefaultTableModel model = new DefaultTableModel(
                new String[]{"Acc No","Holder","Type","Balance (₹)","Overdraft (₹)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        AllAccountsPanel() {
            setBackground(Theme.BG);
            setLayout(new BorderLayout(0, 16));
            setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

            JLabel title = new JLabel("All Accounts");
            title.setFont(new Font("Serif", Font.BOLD, 22));
            title.setForeground(Theme.GOLD);
            add(title, BorderLayout.NORTH);

            JTable table = new JTable(model);
            styleTable(table);
            JScrollPane scroll = new JScrollPane(table);
            styleScroll(scroll);

            SectionCard card = new SectionCard("Account Registry");
            card.add(scroll, BorderLayout.CENTER);
            add(card, BorderLayout.CENTER);
            refresh();
        }

        void refresh() {
            model.setRowCount(0);
            for (Account a : bank.accounts.values()) {
                model.addRow(new Object[]{
                    a.accNo, a.holder, a.type,
                    String.format("%,.2f", a.balance),
                    String.format("%,.2f", a.overdraft)
                });
            }
        }
    }

    // ══════════════════════════════════════════
    //  SHARED UTILITIES
    // ══════════════════════════════════════════

    private void addRow(JPanel p, GridBagConstraints g, int row, String lbl, JComponent field) {
        g.gridx = 0; g.gridy = row;
        JLabel l = new JLabel(lbl);
        l.setFont(Theme.LABEL);
        l.setForeground(Theme.TEXT_DIM);
        p.add(l, g);
        g.gridx = 1;
        p.add(field, g);
    }

    private void styleTable(JTable t) {
        t.setBackground(Theme.CARD);
        t.setForeground(Theme.TEXT);
        t.setFont(Theme.BODY);
        t.setRowHeight(30);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setSelectionBackground(Theme.HIGHLIGHT);
        t.setSelectionForeground(Theme.GOLD);
        t.getTableHeader().setBackground(Theme.SURFACE);
        t.getTableHeader().setForeground(Theme.GOLD);
        t.getTableHeader().setFont(Theme.LABEL);
        t.getTableHeader().setBorder(BorderFactory.createMatteBorder(0,0,1,0,Theme.BORDER));
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                setBackground(sel ? Theme.HIGHLIGHT : (row%2==0 ? Theme.CARD : Theme.SURFACE));
                setForeground(sel ? Theme.GOLD : Theme.TEXT);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
    }

    private void styleScroll(JScrollPane s) {
        s.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        s.getViewport().setBackground(Theme.CARD);
        s.getVerticalScrollBar().setBackground(Theme.SURFACE);
        s.getHorizontalScrollBar().setBackground(Theme.SURFACE);
    }

    // ══════════════════════════════════════════
    //  ENTRY POINT
    // ══════════════════════════════════════════

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(bankGUI::new);
    }
}
