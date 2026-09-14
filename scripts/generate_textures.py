import os
from PIL import Image

# 基础路径
assets_dir = r"G:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex"
textures_block_dir = os.path.join(assets_dir, "textures", "blocks")
textures_item_dir = os.path.join(assets_dir, "textures", "items")
textures_fluid_dir = os.path.join(textures_block_dir, "fluids")

def colorize(img_path, target_path, color_overlay):
    try:
        img = Image.open(img_path).convert("RGBA")
        r, g, b, a = img.split()
        
        # 提取目标颜色
        overlay_r, overlay_g, overlay_b = color_overlay
        
        # 对非完全透明的像素进行染色混合（强行偏向橙红色）
        new_r = r.point(lambda i: int(i * 0.4 + overlay_r * 0.6))
        new_g = g.point(lambda i: int(i * 0.4 + overlay_g * 0.6))
        new_b = b.point(lambda i: int(i * 0.4 + overlay_b * 0.6))
        
        out = Image.merge("RGBA", (new_r, new_g, new_b, a))
        out.save(target_path)
    except Exception as e:
        print(f"Error processing {img_path}: {e}")

# 颜色：烬晶 - 橙红色 (255, 69, 0) -> #FF4500
overlay = (255, 69, 0)

# 从深渊幽矿生成烬晶纹理 (块、锭、粒)
colorize(os.path.join(textures_block_dir, "abyssalite_block.png"), os.path.join(textures_block_dir, "pyroclast_crystal_block.png"), overlay)
colorize(os.path.join(textures_item_dir, "abyssalite_ingot.png"), os.path.join(textures_item_dir, "pyroclast_crystal.png"), overlay)
colorize(os.path.join(textures_item_dir, "abyssalite_nugget.png"), os.path.join(textures_item_dir, "pyroclast_crystal_nugget.png"), overlay)

# 流体纹理 (基于 froststeel 流体修改)
colorize(os.path.join(textures_fluid_dir, "froststeel_still.png"), os.path.join(textures_fluid_dir, "pyroclast_crystal_still.png"), overlay)
colorize(os.path.join(textures_fluid_dir, "froststeel_flow.png"), os.path.join(textures_fluid_dir, "pyroclast_crystal_flow.png"), overlay)

# 复制 mcmeta 文件
import shutil
shutil.copy(os.path.join(textures_fluid_dir, "froststeel_still.png.mcmeta"), os.path.join(textures_fluid_dir, "pyroclast_crystal_still.png.mcmeta"))
shutil.copy(os.path.join(textures_fluid_dir, "froststeel_flow.png.mcmeta"), os.path.join(textures_fluid_dir, "pyroclast_crystal_flow.png.mcmeta"))

print("Textures generated.")