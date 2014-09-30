package appeng.transformer;

import java.util.Map;

import appeng.core.AEConfig;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion()
public class AppEngCore extends DummyModContainer implements IFMLLoadingPlugin
{

	public final AppEngCore instance;

	protected final ModMetadata md = new ModMetadata();

	public AppEngCore() {
		instance = this;
		FMLRelaunchLog.info( "[AppEng] Core Init" );
		md.autogenerated = false;
		md.authorList.add( "AlgorithmX2" );
		md.credits = "AlgorithmX2";
		md.modId = getModId();
		md.version = getVersion();
		md.name = getName();
		md.url = "http://ae2.ae-mod.info";
		md.description = "Embedded Coremod for Applied Energistics 2";
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		return true;
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { "appeng.transformer.asm.ASMIntegration" };
	}

	@Override
	public String getModContainerClass()
	{
		return "appeng.transformer.AppEngCore";
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{

	}

	@Override
	public String getModId()
	{
		return "appliedenergistics2-core";
	}

	@Override
	public String getName()
	{
		return "AppliedEnergistics2 Core";
	}

	@Override
	public String getVersion()
	{
		return AEConfig.VERSION;
	}

	@Override
	public String getDisplayVersion()
	{
		return getVersion();
	}

	@Override
	public ModMetadata getMetadata()
	{
		return md;
	}

	@Override
	public String getAccessTransformerClass()
	{
		return "appeng.transformer.asm.ASMTweaker";
	}
}