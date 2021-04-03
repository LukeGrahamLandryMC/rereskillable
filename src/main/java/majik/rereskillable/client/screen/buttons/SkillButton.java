package majik.rereskillable.client.screen.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import majik.rereskillable.Configuration;
import majik.rereskillable.client.screen.SkillScreen;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.network.RequestLevelUp;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillButton extends AbstractButton
{
    private final Skill skill;
    
    public SkillButton(int x, int y, Skill skill)
    {
        super(x, y, 79, 32, StringTextComponent.EMPTY);
        
        this.skill = skill;
    }
    
    // Render
    
    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.textureManager.bind(SkillScreen.RESOURCES);
    
        int level = SkillModel.get().getSkillLevel(skill);
        int maxLevel = Configuration.getMaxLevel();
        int u = Math.min(level, maxLevel - 1) / (maxLevel / 4) * 16 + 176;
        int v = skill.index * 16 + 128;
        
        blit(stack, x, y, 176, (level == maxLevel ? 64 : 0) + (isMouseOver(mouseX, mouseY) ? 32 : 0), width, height);
        blit(stack, x + 6, y + 8, u, v, 16, 16);
        
        minecraft.font.draw(stack, new TranslationTextComponent(skill.displayName), x + 25, y + 7, 0xFFFFFF);
        minecraft.font.draw(stack, String.format("%d/%d", level, maxLevel), x + 25, y + 18, 0xBEBEBE);
    }
    
    // Press
    
    @Override
    public void onPress()
    {
        RequestLevelUp.send(skill);
    }
}