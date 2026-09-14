import os
import random
from PIL import Image

def generate_textures():
    assets_dir = r"G:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures"
    os.makedirs(os.path.join(assets_dir, 'blocks'), exist_ok=True)
    os.makedirs(os.path.join(assets_dir, 'items'), exist_ok=True)

    ore_path = os.path.join(assets_dir, r"blocks\pyroclast_crystal_ore.png")
    block_path = os.path.join(assets_dir, r"blocks\pyroclast_crystal_block.png")
    ingot_path = os.path.join(assets_dir, r"items\pyroclast_crystal.png")
    nugget_path = os.path.join(assets_dir, r"items\pyroclast_crystal_nugget.png")

    COLORS = {
        'CRUST_DARK': (90, 20, 10, 255),
        'MAGMA_RED': (180, 40, 0, 255), 
        'FIRE_ORANGE': (240, 90, 0, 255),
        'GLOW_YELLOW': (255, 180, 0, 255),
        'CORE_WHITE': (255, 240, 200, 255),
        'STONE_BASE': (80, 75, 75, 255),
    }
    
    C_MAP = {1: 'CRUST_DARK', 2: 'MAGMA_RED', 3: 'FIRE_ORANGE', 4: 'GLOW_YELLOW', 5: 'CORE_WHITE'}
    
    BLK_MAP = {
        0: (40, 10, 5, 255),       
        1: (200, 70, 20, 255),     
        2: COLORS['CRUST_DARK'],   
        3: COLORS['MAGMA_RED'],    
        4: COLORS['FIRE_ORANGE'],  
        5: COLORS['GLOW_YELLOW'],  
        6: COLORS['CORE_WHITE'],   
    }

    # 1. 矿石
    img_ore = Image.new('RGBA', (16, 16))
    pix = img_ore.load()
    pattern_ore = [
        [0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0],
        [0,0,0,0,0,0,1,2,3,2,1,0,0,0,0,0],
        [0,0,0,0,0,1,2,4,5,4,2,1,0,0,0,0],
        [0,1,1,0,1,2,4,5,5,4,2,1,0,0,0,0],
        [1,2,2,1,2,3,4,4,3,2,1,0,0,0,0,0],
        [1,3,4,2,2,2,2,1,1,0,0,0,1,1,1,0],
        [0,1,2,3,2,1,0,0,0,0,0,1,2,3,2,1],
        [0,0,1,2,1,0,0,0,0,0,1,2,4,4,3,1],
        [0,0,0,1,0,0,1,1,1,1,2,3,5,4,2,0],
        [0,0,0,0,0,1,2,3,4,3,2,2,3,2,1,0],
        [0,0,0,0,1,2,4,5,4,3,2,1,1,1,0,0],
        [0,0,0,1,2,3,4,4,2,2,1,0,0,0,0,0],
        [0,0,0,1,2,2,1,1,0,0,0,0,0,0,0,0],
        [0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ]
    random.seed(42)
    for y in range(16):
        for x in range(16):
            if pattern_ore[y][x] > 0:
                pix[x,y] = COLORS[C_MAP[pattern_ore[y][x]]]
            else:
                r = COLORS['STONE_BASE'][0] + random.randint(-5, 5)
                g = COLORS['STONE_BASE'][1] + random.randint(-5, 5)
                b = COLORS['STONE_BASE'][2] + random.randint(-5, 5)
                pix[x,y] = (r, g, b, 255)
    img_ore.save(ore_path)

    # 2. 方块
    img_blk = Image.new('RGBA', (16, 16))
    pix = img_blk.load()
    pattern_blk = [
        [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
        [1,5,4,3,2,1,2,3,4,3,2,1,2,3,4,0],
        [1,4,3,2,1,0,1,2,3,2,1,0,1,2,3,0],
        [1,3,2,1,0,0,0,1,2,1,0,0,0,1,2,0],
        [1,2,1,0,0,2,3,4,5,4,3,2,0,0,1,0],
        [1,1,0,0,1,3,5,6,6,5,3,1,0,0,1,0],
        [1,2,1,0,2,4,6,6,6,6,4,2,0,1,2,0],
        [1,3,2,1,3,5,6,6,6,6,5,3,1,2,3,0],
        [1,4,3,2,4,6,6,6,6,6,4,2,3,4,5,0],
        [1,3,2,1,3,5,6,6,6,5,3,1,2,3,4,0],
        [1,2,1,0,2,4,5,6,5,4,2,0,1,2,3,0],
        [1,1,0,0,1,3,4,5,4,3,1,0,0,1,2,0],
        [1,0,0,1,2,1,3,4,3,1,2,1,0,0,1,0],
        [1,0,1,2,3,2,1,2,1,2,3,2,1,0,1,0],
        [1,1,2,3,4,3,2,1,2,3,4,3,2,1,1,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ]
    for y in range(16):
        for x in range(16):
            pix[x,y] = BLK_MAP[pattern_blk[y][x]]
    img_blk.save(block_path)

    # 3. 锭 (水晶)
    img_item = Image.new('RGBA', (16, 16))
    pix = img_item.load()
    pattern_item = [
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,5,4,1,0],
        [0,0,0,0,0,0,0,0,0,0,0,5,4,3,1,0],
        [0,0,0,0,0,0,0,0,0,0,5,4,3,2,1,0],
        [0,0,0,0,0,0,0,0,0,5,6,4,3,2,1,0],
        [0,0,0,0,0,0,0,0,4,6,5,4,2,1,0,0],
        [0,0,0,0,0,0,0,4,6,5,4,3,1,0,0,0],
        [0,0,0,0,0,0,3,5,4,3,2,1,0,0,0,0],
        [0,0,0,0,0,2,4,3,2,2,1,0,0,0,0,0],
        [0,0,0,0,1,3,2,2,1,1,0,0,0,0,0,0],
        [0,0,0,1,2,1,1,1,0,0,0,0,0,0,0,0],
        [0,0,1,2,1,0,0,0,0,0,0,0,0,0,0,0],
        [0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ]
    for y in range(16):
        for x in range(16):
            if pattern_item[y][x] > 0:
                pix[x,y] = BLK_MAP[pattern_item[y][x]]
    img_item.save(ingot_path)

    # 4. 粒
    img_nugget = Image.new('RGBA', (16, 16))
    pix = img_nugget.load()
    pattern_nugget = [
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0],
        [0,0,0,0,0,1,2,3,2,1,0,0,0,0,0,0],
        [0,0,0,0,1,3,4,5,3,1,0,0,0,0,0,0],
        [0,0,0,0,1,2,5,6,4,2,1,0,0,0,0,0],
        [0,0,0,0,0,1,3,4,2,1,0,0,0,0,0,0],
        [0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ]
    for y in range(16):
        for x in range(16):
            if pattern_nugget[y][x] > 0:
                pix[x,y] = BLK_MAP[pattern_nugget[y][x]]
    img_nugget.save(nugget_path)

    print("Success: Generated all 4 texture files directly via local python script.")

if __name__ == "__main__":
    generate_textures()
