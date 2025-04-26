my_dictionary = set()

with open('aliceInWonderlandDictionary.txt','r') as inputFile:
    line = inputFile.readline()
    while line:
        word = line.strip()
        my_dictionary.add(word)
        
        line = inputFile.readline()
        

words = ['alice', 'tacos', 'lice', 'william', 'handsome', 'aliceinwonderland']

for word in words:
    if word in my_dictionary:
        print(word,'is in the dictionary.')
    else:
        print(word,'is not in the dictionary.')
