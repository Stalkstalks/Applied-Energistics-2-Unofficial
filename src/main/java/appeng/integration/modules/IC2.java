/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.integration.modules;


import java.util.function.BiFunction;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import ic2.api.recipe.RecipeInputItemStack;

import appeng.api.AEApi;
import appeng.api.config.TunnelType;
import appeng.api.features.IP2PTunnelRegistry;
import appeng.helpers.Reflected;
import appeng.integration.IIntegrationModule;
import appeng.integration.IntegrationHelper;
import appeng.integration.abstraction.IIC2;
import appeng.integration.modules.ic2.IC2PowerSink;
import appeng.integration.modules.ic2.IC2PowerSinkAdapter;
import appeng.integration.modules.ic2.IC2PowerSinkStub;
import appeng.tile.powersink.IExternalPowerSink;


public class IC2 implements IIntegrationModule, IIC2
{

	@Reflected
	public static IC2 instance;

	public IC2()
	{
		IntegrationHelper.testClassExistence( this, ic2.api.energy.tile.IEnergyTile.class );
		IntegrationHelper.testClassExistence( this, ic2.api.recipe.RecipeInputItemStack.class );
	}

	private static BiFunction<TileEntity, IExternalPowerSink, IC2PowerSink> powerSinkFactory = ( ( te, sink ) -> IC2PowerSinkStub.INSTANCE );

	@Override
	public void init() throws Throwable
	{
		powerSinkFactory = IC2PowerSinkAdapter::new;
	}

	@Override
	public void postInit()
	{
		final IP2PTunnelRegistry reg = AEApi.instance().registries().p2pTunnel();
		reg.addNewAttunement( this.getItem( "copperCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "insulatedCopperCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "goldCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "insulatedGoldCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "ironCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "insulatedIronCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "insulatedTinCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "glassFiberCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "tinCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "detectorCableItem" ), TunnelType.IC2_POWER );
		reg.addNewAttunement( this.getItem( "splitterCableItem" ), TunnelType.IC2_POWER );
	}

	public ItemStack getItem( final String name )
	{
		return ic2.api.item.IC2Items.getItem( name );
	}

	/**
	 * Create an IC2 power sink for the given external sink.
	 */
	public static IC2PowerSink createPowerSink( TileEntity tileEntity, IExternalPowerSink externalSink )
	{
		return powerSinkFactory.apply( tileEntity, externalSink );
	}

	@Override
	public void maceratorRecipe( ItemStack in, ItemStack out )
	{
		ic2.api.recipe.Recipes.macerator.addRecipe( new RecipeInputItemStack( in, in.stackSize ), null, false, out );
	}

}
