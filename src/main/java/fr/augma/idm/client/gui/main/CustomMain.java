package fr.augma.idm.client.gui.main;

import java.io.IOException;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GLContext;

import com.google.common.util.concurrent.Runnables;

import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomMain extends GuiScreen {

	private final ServerData server = new ServerData("iggdrasil", "localhost", false);//"51.68.21.1:14078"
	private static final Logger LOGGER = LogManager.getLogger();
	/** The Object object utilized as a thread lock when performing non thread-safe operations */
	private final Object threadLock = new Object();
	public static final String MORE_INFO_TEXT = "Please click §nhere §rfor more information.";
	/** Width of openGLWarning2 */
	private int openGLWarning2Width;
	/** Width of openGLWarning1 */
	private int openGLWarning1Width;
	/** Left x coordinate of the OpenGL warning */
	private int openGLWarningX1;
	/** Top y coordinate of the OpenGL warning */
	private int openGLWarningY1;
	/** Right x coordinate of the OpenGL warning */
	private int openGLWarningX2;
	/** Bottom y coordinate of the OpenGL warning */
	private int openGLWarningY2;
	/** OpenGL graphics card warning. */
	private String openGLWarning1;
	/** OpenGL graphics card warning. */
	private String openGLWarning2;
	/** Link to the Mojang Support about minimum requirements */
	private String openGLWarningLink;
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(IDMReferences.MODID, "textures/gui/background/background.png");
	private int widthCopyright;
	private int widthCopyrightRest;

	public CustomMain()
	{
		this.openGLWarning2 = MORE_INFO_TEXT;
		FMLClientHandler.instance().setupServerList();

		this.openGLWarning1 = "";

		if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
		{
			this.openGLWarning1 = I18n.format("title.oldgl1");
			this.openGLWarning2 = I18n.format("title.oldgl2");
			this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui()
	{
		this.widthCopyright = this.fontRenderer.getStringWidth("Copyright Mojang AB. Do not distribute!");
		this.widthCopyrightRest = this.width - this.widthCopyright - 2;

		int j = this.height / 4 + 48;

		this.addSingleplayerMultiplayerButtons(j, 24);

		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options")));
		this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit")));
		//this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, j + 72 + 12));

		synchronized (this.threadLock)
		{
			this.openGLWarning1Width = this.fontRenderer.getStringWidth(this.openGLWarning1);
			this.openGLWarning2Width = this.fontRenderer.getStringWidth(this.openGLWarning2);
			int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
			this.openGLWarningX1 = (this.width - k) / 2;
			this.openGLWarningY1 = (this.buttonList.get(0)).y - 24;
			this.openGLWarningX2 = this.openGLWarningX1 + k;
			this.openGLWarningY2 = this.openGLWarningY1 + 24;
		}

		this.mc.setConnectedToRealms(false);
	}

	/**
	 * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
	 */
	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
	{
//		this.buttonList.add(new GuiButton(1, this.width / 2 - 200, p_73969_1_, I18n.format("menu.singleplayer")));
//		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer")));
//		this.realmsButton = this.addButton(new GuiButton(14, this.width / 2 + 2, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("menu.online").replace("Minecraft", "").trim()));
//		this.buttonList.add(modButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods")));
		this.buttonList.add(new GuiButton(7, this.width / 2 - 100, p_73969_1_, "IggDrasil"));
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == 0)
		{
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if(button.id == 7)
		{
			FMLClientHandler.instance().connectToServer(this, this.server);
		}

		if (button.id == 5)
		{
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}

		if (button.id == 1)
		{
			this.mc.displayGuiScreen(new GuiWorldSelection(this));
		}

		if (button.id == 2)
		{
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if (button.id == 4)
		{
			this.mc.shutdown();
		}

		if (button.id == 6)
		{
			this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
		}

		if (button.id == 11)
		{
			this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
		}

		if (button.id == 12)
		{
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

			if (worldinfo != null)
			{
				this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion"), "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning"), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 12));
			}
		}
	}

	public void confirmClicked(boolean result, int id)
	{
		if (result && id == 12)
		{
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			isaveformat.flushCache();
			isaveformat.deleteWorldDirectory("Demo_World");
			this.mc.displayGuiScreen(this);
		}
		else if (id == 12)
		{
			this.mc.displayGuiScreen(this);
		}
		else if (id == 13)
		{
			if (result)
			{
				try
				{
					Class<?> oclass = Class.forName("java.awt.Desktop");
					Object object = oclass.getMethod("getDesktop").invoke((Object)null);
					oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
				}
				catch (Throwable throwable)
				{
					LOGGER.error("Couldn't open link", throwable);
				}
			}

			this.mc.displayGuiScreen(this);
		}
	}

	private void drawCustomBackground() {
		mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1, 1, this.width, this.height, 1, 1);
	}


	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		GlStateManager.disableAlpha();
		this.drawCustomBackground();
		GlStateManager.enableAlpha();

		this.drawString(this.fontRenderer, "Copyright Mojang AB. Do not distribute!", this.widthCopyrightRest, this.height - 10, -1);

		if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height && Mouse.isInsideWindow())
		{
			drawRect(this.widthCopyrightRest, this.height - 1, this.widthCopyrightRest + this.widthCopyright, this.height, -1);
		}

		if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty())
		{
			drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
			this.drawString(this.fontRenderer, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
			this.drawString(this.fontRenderer, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, (this.buttonList.get(0)).y - 12, -1);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		synchronized (this.threadLock)
		{
			if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2)
			{
				GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
				guiconfirmopenlink.disableSecurityWarning();
				this.mc.displayGuiScreen(guiconfirmopenlink);
			}
		}

		if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height)
		{
			this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
		}
	}
}