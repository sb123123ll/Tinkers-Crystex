import os
from PIL import Image

diamond_path = r"G:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures\blocks\diamond_ore.png"
out_path = r"G:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures\blocks\abyssalite_ore.png"

# 设计全新的深渊幽矿结晶簇分布形态 (0: 石头, 1~5: 晶体层级从暗到极亮)
# 放弃散碎点阵，改为三大块集中生长的异形晶簇，极具辨识度
custom_pattern = [
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 4, 3, 1, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 4, 2, 1, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 1, 0, 0, 0],
    [0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0],
    [1, 2, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [1, 3, 5, 4, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 1, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0],
    [0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 2, 2, 1, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 4, 3, 2, 1, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 5, 5, 4, 2, 1, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 4, 2, 1, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0]
]

# 深渊幽矿专属晶体色阶
colors = {
    1: (0, 20, 45, 255),    # 渊底暗蓝（深海高压边缘）
    2: (0, 75, 120, 255),   # 幽深海蓝（结晶外壳）
    3: (0, 160, 200, 255),  # 充能青蓝（发光过渡层）
    4: (100, 240, 255, 255),# 高亮微光青色（高能量区）
    5: (220, 255, 255, 255) # 极白核心光斑（极压能量核心）
}

img = Image.open(diamond_path).convert("RGBA")
pixels = img.load()

# 步骤 1：剥离原版钻石像素，提取纯净的石头基底纹理
stone_base = {}
for y in range(16):
    for x in range(16):
        r, g, b, a = pixels[x, y]
        # RGB差值过大说明是钻石晶体
        is_diamond = max(r,g,b) - min(r,g,b) > 15
        if not is_diamond:
            stone_base[(x,y)] = (r,g,b)
            
# 智能填补被剥离的空洞（用周围最近的石头像素填充，保持原版石头的自然噪点）
for y in range(16):
    for x in range(16):
        if (x,y) not in stone_base:
            for radius in range(1, 16):
                found = False
                for dy in range(-radius, radius+1):
                    for dx in range(-radius, radius+1):
                        nx, ny = x+dx, y+dy
                        if (nx, ny) in stone_base:
                            stone_base[(x,y)] = stone_base[(nx, ny)]
                            found = True
                            break
                    if found: break
                if found: break

# 步骤 2：应用深渊环境滤镜与全新的晶簇形态
for y in range(16):
    for x in range(16):
        val = custom_pattern[y][x]
        if val > 0:
            # 绘制独特的深渊幽矿结晶簇
            pixels[x, y] = colors[val]
        else:
            sr, sg, sb = stone_base[(x,y)]
            # 深海岩层化：大幅压暗亮度，并赋予深海特有的冷青蓝色调
            base_r = int(sr * 0.40)
            base_g = int(sg * 0.50)
            base_b = int(sb * 0.65)
            pixels[x, y] = (base_r, base_g, base_b, 255)
            
# 步骤 3：添加高级发光晕染 (Bloom) 效果
# 让晶体的高能量区域在深色石头上产生真实的青色光晕映射
for y in range(16):
    for x in range(16):
        if custom_pattern[y][x] == 0:
            glow_factor = 0
            # 检测周围的高光像素
            for dy in range(-2, 3):
                for dx in range(-2, 3):
                    nx, ny = x+dx, y+dy
                    if 0 <= nx < 16 and 0 <= ny < 16:
                        v = custom_pattern[ny][nx]
                        if v >= 3: # 只有较亮的晶体会产生光晕
                            dist = abs(dx) + abs(dy)
                            if dist == 1: glow_factor += 0.35
                            elif dist == 2: glow_factor += 0.12
            
            if glow_factor > 0:
                pr, pg, pb, pa = pixels[x, y]
                # 荧光青色的光晕色
                glow_r, glow_g, glow_b = 0, 210, 255
                glow_factor = min(0.65, glow_factor)
                nr = int(pr * (1-glow_factor) + glow_r * glow_factor)
                ng = int(pg * (1-glow_factor) + glow_g * glow_factor)
                nb = int(pb * (1-glow_factor) + glow_b * glow_factor)
                pixels[x, y] = (nr, ng, nb, 255)

img.save(out_path)
print("Deeply optimized Abyssalite texture successfully generated!")