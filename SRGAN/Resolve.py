# Resolve

from SRGAN.model.srgan import generator
from SRGAN.model import resolve_single
import os

def resolve(image):
    weights_dir = 'SRGAN/weights/srgan'
    weights_file = lambda filename: os.path.join(weights_dir, filename)
        
    gan_generator = generator()
    gan_generator.load_weights(weights_file('gan_generator.h5'))
	
    return resolve_single(gan_generator, image)