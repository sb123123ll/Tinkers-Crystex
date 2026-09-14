import os
from PIL import Image

diamond_path = r"G:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures\blocks\diamond_ore.png"
out_path = r"G:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures\blocks\abyssalite_ore.png"

if not os.path.exists(diamond_path):
    print(f"Error: Could not find {diamond_path}")
    exit(1)

img = Image.open(diamond_path).convert("RGBA")
pixels = img.load()

for y in range(img.size[1]):
    for x in range(img.size[0]):
        r, g, b, a = pixels[x, y]
        if a == 0: continue
        
        # 通过 RGB 通道的最大差值来区分原石和矿物晶体
        # 原版石头几乎是纯灰色（R=G=B），差值极小；而钻石晶体颜色饱和度高，差值大
        diff = max(r, g, b) - min(r, g, b)
        is_crystal = diff > 15
        
        if is_crystal:
            # 提取原版晶体的亮度，重映射为深渊幽矿 (Abyssalite) 的色彩渐变
            lum = (r + g + b) / 3.0
            if lum > 220:
                pixels[x, y] = (160, 255, 255, a) # 最亮高光：微光青色
            elif lum > 160:
                pixels[x, y] = (0, 190, 230, a)   # 次亮部：明亮的青蓝色
            elif lum > 100:
                pixels[x, y] = (0, 60, 140, a)    # 中间调：深海幽蓝 (核心色)
            elif lum > 50:
                pixels[x, y] = (0, 25, 80, a)     # 暗部：深邃暗蓝
            else:
                pixels[x, y] = (0, 10, 40, a)     # 极暗部：几乎融入阴影的渊底色
        else:
            # 原石基底：保留原版石头的明暗纹理，仅添加冷色调的深海幽暗滤镜
            nr = int(r * 0.75)  # 压暗红光
            ng = int(g * 0.82)  # 适当保留绿光
            nb = int(b * 0.90)  # 突出蓝光，增加冷冽感
            pixels[x, y] = (min(255, nr), min(255, ng), min(255, nb), a)

img.save(out_path)
print("Texture successfully recreated based on diamond_ore.png")
