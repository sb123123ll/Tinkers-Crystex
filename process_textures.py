import os
import io
import math
import random
from PIL import Image, ImageEnhance, ImageFilter, ImageDraw

def adjust_color(img, color_shift=(0, 0, 0), saturation=1.0, brightness=1.0, contrast=1.0):
    if img.mode != 'RGBA':
        img = img.convert('RGBA')
    
    pixels = img.load()
    width, height = img.size
    
    for x in range(width):
        for y in range(height):
            r, g, b, a = pixels[x, y]
            if a > 0:
                # 霜寒色调偏蓝/青色
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
    
    return img

def add_frost_crystals(img, intensity=0.2, frost_color=(230, 245, 255)):
    if img.mode != 'RGBA':
        img = img.convert('RGBA')
    
    result = img.copy()
    pixels = result.load()
    orig_pixels = img.load()
    width, height = result.size
    
    for x in range(width):
        for y in range(height):
            r, g, b, a = orig_pixels[x, y]
            if a > 0:
                # 随机生成冰霜颗粒感
                if random.random() < intensity:
                    # 边缘或亮部更容易结霜
                    luminance = (r + g + b) / 3
                    if luminance > 120 or random.random() < 0.3:
                        frost_alpha = int(random.uniform(100, 200) * (a / 255.0))
                        
                        # Alpha混合
                        out_r = (frost_color[0] * frost_alpha + r * (255 - frost_alpha)) // 255
                        out_g = (frost_color[1] * frost_alpha + g * (255 - frost_alpha)) // 255
                        out_b = (frost_color[2] * frost_alpha + b * (255 - frost_alpha)) // 255
                        
                        pixels[x, y] = (out_r, out_g, out_b, a)
    
    return result

def add_frost_edges(img, thickness=1, color=(240, 250, 255, 180)):
    if img.mode != 'RGBA':
        img = img.convert('RGBA')
        
    result = img.copy()
    pixels = result.load()
    orig_pixels = img.load()
    width, height = result.size
    
    for x in range(width):
        for y in range(height):
            r, g, b, a = orig_pixels[x, y]
            if a > 0:
                # 检测边缘
                is_edge = False
                for dx in range(-thickness, thickness + 1):
                    for dy in range(-thickness, thickness + 1):
                        nx, ny = x + dx, y + dy
                        if 0 <= nx < width and 0 <= ny < height:
                            _, _, _, na = orig_pixels[nx, ny]
                            if na == 0:
                                is_edge = True
                                break
                    if is_edge:
                        break
                
                if is_edge and random.random() < 0.7:
                    # 在边缘添加冰霜高光
                    blend_alpha = color[3]
                    out_r = (color[0] * blend_alpha + r * (255 - blend_alpha)) // 255
                    out_g = (color[1] * blend_alpha + g * (255 - blend_alpha)) // 255
                    out_b = (color[2] * blend_alpha + b * (255 - blend_alpha)) // 255
                    pixels[x, y] = (out_r, out_g, out_b, a)
                    
    return result

def process_texture(filepath, output_path, type_str):
    try:
        img = Image.open(filepath)
        
        # 1. 基础色调调整（增强冷色调，降低饱和度增加清冷感，提高亮度体现冰雪反光）
        img_adjusted = adjust_color(img, color_shift=(-10, 10, 30), saturation=0.8, brightness=1.1, contrast=1.15)
        
        # 2. 添加细碎的霜花结晶
        intensity = 0.15 if type_str == 'block' else 0.2
        img_frosted = add_frost_crystals(img_adjusted, intensity=intensity)
        
        # 3. 添加边缘凝霜效果 (特别针对物品)
        if type_str in ['ingot', 'nugget', 'ore']:
            img_final = add_frost_edges(img_frosted, thickness=1)
        else:
            img_final = img_frosted
            
        img_final.save(output_path)
        print(f"Processed: {filepath} -> {output_path}")
        return True
    except Exception as e:
        print(f"Error processing {filepath}: {e}")
        return False

import shutil

def main():
    base_dir = "G:/myfirstmod/1.12.2/Tinkers' Crystex/src/main/resources/assets/tinkerscrystex/textures/"
    
    files_to_process = [
        ("items/frost_steel_ingot.png", "ingot"),
        ("items/frost_steel_nugget.png", "nugget"),
        ("blocks/frost_steel_ore.png", "ore"),
        ("blocks/frost_steel_block.png", "block"),
        ("blocks/fluids/froststeel_still.png", "fluid"),
        ("blocks/fluids/froststeel_flow.png", "fluid")
    ]
    
    # 备份原文件
    backup_dir = "G:/myfirstmod/1.12.2/Tinkers' Crystex/texture_backups/"
    os.makedirs(backup_dir, exist_ok=True)
    
    for rel_path, type_str in files_to_process:
        full_path = os.path.join(base_dir, rel_path)
        if os.path.exists(full_path):
            filename = os.path.basename(rel_path)
            backup_path = os.path.join(backup_dir, f"orig_{filename}")
            shutil.copy2(full_path, backup_path)
            
            output_path = os.path.join(backup_dir, f"new_{filename}")
            process_texture(full_path, output_path, type_str)
            
            # 直接替换原文件
            shutil.copy2(output_path, full_path)
        else:
            print(f"File not found: {full_path}")

if __name__ == "__main__":
    main()
