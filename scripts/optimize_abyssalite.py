import os
from PIL import Image, ImageEnhance, ImageFilter

assets_dir = r"G:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures"
paths = [
    r"blocks\abyssalite_block.png",
    r"blocks\abyssalite_ore.png",
    r"items\abyssalite_ingot.png",
    r"items\abyssalite_nugget.png"
]

def optimize_texture(rel_path):
    abs_path = os.path.join(assets_dir, rel_path)
    if not os.path.exists(abs_path):
        print(f"File not found: {abs_path}")
        return
    
    img = Image.open(abs_path).convert("RGBA")
    
    # 1. 增强对比度 (强化光影层次，拉开明暗对比)
    enhancer = ImageEnhance.Contrast(img)
    img = enhancer.enhance(1.3)
    
    # 2. 增强颜色饱和度 (让颜色更纯粹)
    color_enhancer = ImageEnhance.Color(img)
    img = color_enhancer.enhance(1.2)
    
    # 3. 像素级微光与深邃感处理
    pixels = img.load()
    for y in range(img.size[1]):
        for x in range(img.size[0]):
            r, g, b, a = pixels[x, y]
            if a > 0: # 忽略透明像素
                lum = 0.299*r + 0.587*g + 0.114*b
                
                # 暗部深化：压暗暗部，并微微向深海蓝偏移，增强深邃感
                if lum < 80:
                    pixels[x, y] = (int(r*0.8), int(g*0.8), min(255, int(b*1.1)), a)
                # 亮部微光：提取高光部分，向青色(Cyan)色调偏移，模拟深海中特有的微光流动
                elif lum > 160:
                    pixels[x, y] = (int(r*0.85), min(255, int(g*1.25)), min(255, int(b*1.35)), a)
    
    # 4. 边缘锐化处理 (提升 Mipmap 缩小后的清晰度，适配远距离渲染)
    # 因为是 16x16 像素画，用基础的 Sharpen 可以让像素交界处更清晰
    img = img.filter(ImageFilter.SHARPEN)
    
    img.save(abs_path)
    print(f"Optimized: {rel_path}")

for p in paths:
    optimize_texture(p)
print("Texture optimization complete.")
