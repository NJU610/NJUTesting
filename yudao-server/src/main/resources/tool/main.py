import sys
import os
import getopt

def main(argv):
    i_path = ''
    table = ''
    output = ''
    try:
      opts, _ = getopt.getopt(argv,'-h-t:-o:-i:',["help","table","ofile","ifile"])
      print(opts)
    except getopt.GetoptError:
      print("main.py -t <table> -o <output> -i <input>")
      sys.exit(2)
    for opt, arg in opts:
        if opt == "-h":
            print("main.py -t <table> -o <output> -i <input>")
            sys.exit()
        elif opt in ("-t", "--table"):
            table = arg
        elif opt in ("-o", "--ofile"):
            output = arg
        elif opt in ("-i", "--ifile"):
            i_path = arg
    print(table + " " + output + " " + i_path)
    if table == 'JS002':
        pass
    elif table == 'JS003':
        pass
    elif table == 'JS004':
        pass
    elif table == 'JS005':
        pass
    elif table == 'JS006':
        command = 'C:/Users/Yongp/AppData/Local/Programs/Python/Python310/python.exe c:/Users/Yongp/Desktop/Classified/NJUTesting/yudao-server/src/main/resources/tool/JS006/test.py' \
                  + " -o " \
                  + output \
                  + ' -i ' \
                  + i_path
        print(command)
        os.system(command)
    elif table == 'JS007':
        pass
    elif table == 'JS008':
        pass
    elif table == 'JS009':
        pass
    elif table == 'JS010':
        pass
    elif table == 'JS011':
        pass
    elif table == 'JS012':
        pass
    elif table == 'JS013':
        pass
    elif table == 'JS014':
        pass
    elif table == 'offer':
        pass

if __name__ == '__main__':
   main(sys.argv[1:])
   
   #python c:/Users/Yongp/Desktop/Classified/NJUTesting/yudao-server/src/main/resources/tool/main.py -t 'JS006' -o 'output' -i "C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS006\\data.json" 