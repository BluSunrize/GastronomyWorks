package blusunrize.gastronomyworks.items;

import blusunrize.gastronomyworks.GWRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

public class CannedFood extends Item
{
	public CannedFood(Properties pProperties)
	{
		super(pProperties);
	}

	@Override
	public SoundEvent getEatingSound()
	{
		return SoundEvents.EMPTY; // we're doing sounds on our own timescale
	}

	@Override
	public void onUseTick(Level level, LivingEntity living, ItemStack stack, int remainingUseDuration)
	{
		// wait until after bottle opening sound
		if(remainingUseDuration < getUseDuration(stack)-18&&remainingUseDuration%4==0)
			living.playSound(
					SoundEvents.GENERIC_EAT,
					0.5F+0.5F*(float)living.getRandom().nextInt(2),
					(living.getRandom().nextFloat()-living.getRandom().nextFloat())*0.2F+1.0F
			);
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		if(!player.canEat(false))
			return InteractionResultHolder.fail(player.getItemInHand(hand));
		player.playSound(GWRegistration.Sounds.CAN_OPENING.get(), 1f, level.random.nextFloat()*0.1f+0.9f);
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public int getUseDuration(ItemStack stack)
	{
		return 60; // takes longer to eat than normal food to account for can opening
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity)
	{
		ItemStack ret = livingEntity.eat(level, stack);
		if(ret.isEmpty())
			return new ItemStack(GWRegistration.Items.TIN_CAN.asItem());
		if(livingEntity instanceof Player player)
			player.getInventory().add(new ItemStack(GWRegistration.Items.TIN_CAN.asItem()));
		return ret;
	}
}
