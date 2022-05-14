package github.qe7.detect.ui.altmenu;

import java.io.IOException;
import java.util.Random;

import github.qe7.detect.ui.impl.menu.AltLoginThread;
import github.qe7.detect.ui.impl.menu.PasswordField;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;

public class GuiAltManager extends GuiScreen {
	private PasswordField password;
    private final GuiScreen previousScreen;
    private AltLoginThread thread;
    private GuiTextField username;

    public GuiAltManager(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                if(this.username.getText().length() > 0) {
                    this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                    this.thread.start();
                }
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 68: {
                this.thread = new AltLoginThread(this.genString(), "");
                this.thread.start();
            }
            case 69: {
            	if(getClipboardString() != null && getClipboardString().split(":").length > 1) {
	                this.thread = new AltLoginThread(getClipboardString().split(":")[0], getClipboardString().split(":")[1]);
	                this.thread.start();
            	}
            }
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        Gui.drawCenteredString(fontRendererObj, this.thread == null ? (Object)((Object)EnumChatFormatting.GRAY) + "Current User : " + username.getText() : this.thread.getStatus(), width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
        	Gui.drawCenteredString(fontRendererObj,"Username", width / 2 - 54 + 7, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            Gui.drawCenteredString(fontRendererObj,"Password", width / 2 - 74 + 4, 106, -7829368);
        }
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        int var3 = height / 4 + 24;
        this.buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(68, width / 2 - 100, var3 + 72 + 12 + 24, "Random cracked"));
        this.buttonList.add(new GuiButton(69, width / 2 - 100, var3 + 72 + 12 + 24 + 24, "Clipboard Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 72 + 12 + 24 + 24+ 24, "Back"));
        this.buttonList.add(new GuiButton(2,-50,-50,-50,-50, "Back2"));
        this.username = new GuiTextField(var3, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        if(mc.getSession().getUsername() != null)
        	username.setText(mc.getSession().getUsername());
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            } else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (character == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }

    @Override
	public void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }

    public String genString() {
    	int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = "_" + random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
        
        return generatedString;
    }
}
