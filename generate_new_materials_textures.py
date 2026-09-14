import os
import random
from PIL import Image, ImageEnhance, ImageFilter

def process_texture(filepath, output_path, type_str, color_shift=(-20, -10, 40), brightness=0.9, contrast=1.2, saturation=1.2):
    try:
        img = Image.open(filepath)
        if img.mode != 'RGBA':
            img = img.convert('RGBA')
            
        width, height = img.size
        pixels = img.load()
        
        # 1. 基础色调调整
        for x in range(width):
            for y in range(height):
                r, g, b, a = pixels[x, y]
                if a > 0:
                    nr = min(255, max(0, r + color_shift[0]))
                    ng = min(255, max(0, g + color_shift[1]))
                    nb = min(255, max(0, b + color_shift[2]))
                    pixels[x, y] = (nr, ng, nb, a)
                    
        enhancer_s = ImageEnhance.Color(img)
        img = enhancer_s.enhance(saturation)
        
        enhancer_b = ImageEnhance.Brightness(img)
        img = enhancer_b.enhance(brightness)
        
        enhancer_c = ImageEnhance.Contrast(img)
        img = enhancer_c.enhance(contrast)
        
        pixels = img.load()
        
        # 2. 深海压强水波纹和发光斑点
        if type_str == 'ore':
            for i in range(15): # 随机发光斑点
                x, y = random.randint(0, width-1), random.randint(0, height-1)
                r, g, b, a = pixels[x, y]
                if a > 0:
                    pixels[x, y] = (0, 200, 255, 255) # 青色斑点
                    if x > 0: pixels[x-1, y] = (0, 150, 200, max(150, pixels[x-1, y][3]))
                    if y > 0: pixels[x, y-1] = (0, 150, 200, max(150, pixels[x, y-1][3]))
        
        elif type_str == 'ingot':
            for y in range(height):
                for x in range(width):
                    r, g, b, a = pixels[x, y]
                    if a > 0:
                        # 水波纹效果
                        wave = (random.random() * 20) if (x+y)%3==0 else 0
                        nr = min(255, max(0, int(r - wave)))
                        ng = min(255, max(0, int(g + wave)))
                        nb = min(255, max(0, int(b + wave*1.5)))
                        pixels[x, y] = (nr, ng, nb, a)
        
        # 保存
        img.save(output_path)
        print(f"Generated: {output_path}")
        return True
    except Exception as e:
        print(f"Error processing {filepath}: {e}")
        return False

def main():
    # 基础文件目录
    base_src_dir = "G:/myfirstmod/1.12.2/Tinkers' Crystex/src/main/resources/assets/tinkerscrystex/textures/"
    # 原始的霜钢文件用于做底板
    src_files = {
        "ore": os.path.join(base_src_dir, "blocks/frost_steel_ore.png"),
        "block": os.path.join(base_src_dir, "blocks/frost_steel_block.png"),
        "ingot": os.path.join(base_src_dir, "items/frost_steel_ingot.png"),
        "nugget": os.path.join(base_src_dir, "items/frost_steel_nugget.png"),
        "fluid_still": os.path.join(base_src_dir, "blocks/fluids/froststeel_still.png"),
        "fluid_flow": os.path.join(base_src_dir, "blocks/fluids/froststeel_flow.png")
    }

    # === 生成深渊幽矿 (Abyssalite) ===
    # 深色海蓝色
    process_texture(src_files["ore"], os.path.join(base_src_dir, "blocks/abyssalite_ore.png"), "ore", (-50, -20, 50), 0.7, 1.3, 1.4)
    process_texture(src_files["block"], os.path.join(base_src_dir, "blocks/abyssalite_block.png"), "block", (-40, -10, 60), 0.8, 1.2, 1.3)
    process_texture(src_files["ingot"], os.path.join(base_src_dir, "items/abyssalite_ingot.png"), "ingot", (-30, 0, 70), 0.85, 1.3, 1.5)
    process_texture(src_files["nugget"], os.path.join(base_src_dir, "items/abyssalite_nugget.png"), "nugget", (-30, 0, 70), 0.85, 1.3, 1.5)
    process_texture(src_files["fluid_still"], os.path.join(base_src_dir, "blocks/fluids/abyssalite_still.png"), "fluid", (-50, -20, 80), 0.9, 1.1, 1.4)
    process_texture(src_files["fluid_flow"], os.path.join(base_src_dir, "blocks/fluids/abyssalite_flow.png"), "fluid", (-50, -20, 80), 0.9, 1.1, 1.4)

    # === 生成虚空合金 (Void Alloy) ===
    # 紫黑色调
    process_texture(src_files["block"], os.path.join(base_src_dir, "blocks/void_alloy_block.png"), "block", (-20, -50, 40), 0.6, 1.4, 1.1)
    process_texture(src_files["ingot"], os.path.join(base_src_dir, "items/void_alloy_ingot.png"), "ingot", (-20, -50, 40), 0.7, 1.4, 1.2)
    process_texture(src_files["nugget"], os.path.join(base_src_dir, "items/void_alloy_nugget.png"), "nugget", (-20, -50, 40), 0.7, 1.4, 1.2)
    process_texture(src_files["fluid_still"], os.path.join(base_src_dir, "blocks/fluids/void_alloy_still.png"), "fluid", (-20, -50, 40), 0.7, 1.2, 1.1)
    process_texture(src_files["fluid_flow"], os.path.join(base_src_dir, "blocks/fluids/void_alloy_flow.png"), "fluid", (-20, -50, 40), 0.7, 1.2, 1.1)

    # === 生成紫颂果汁 (Liquid Chorus) ===
    # 紫色
    process_texture(src_files["fluid_still"], os.path.join(base_src_dir, "blocks/fluids/liquid_chorus_still.png"), "fluid", (50, -40, 50), 1.0, 1.0, 1.3)
    process_texture(src_files["fluid_flow"], os.path.join(base_src_dir, "blocks/fluids/liquid_chorus_flow.png"), "fluid", (50, -40, 50), 1.0, 1.0, 1.3)

if __name__ == "__main__":
    main()
