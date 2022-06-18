
from platform import platform
from pydoc import plain
from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import getopt
import sys
from docx2pdf import convert
import os
import platform
from mailmerge import MailMerge  # 引用邮件处理模块

# -t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS012\NST－04－JS012－2011－软件项目委托测试工作检查表.docx" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS012\output" -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS012\data.json"

def main(argv):
  o_path = ""
  i_path = ""
  template = ""
  
  try:
    opts, args = getopt.getopt(argv,'o:i:t:',["ofile=","ifile=","template="])
  except getopt.GetoptError:
    sys.exit(2)
    
  for opt, arg in opts:
      if opt in ("-o", "--ofile"):
          o_path = arg
      elif opt in ("-i", "--ifile"):
          i_path = arg 
      elif opt in ("-t", "--template"):
          template = arg
          
  with open(i_path, 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    document = MailMerge(template)

    document.merge(
      SoftName = jdata["软件名称"],
      Version = jdata["版本号"],
      Applicator = jdata["申报单位"],
      StartTime = jdata["起始时间"],
      ExpectedTime = jdata["预计完成时间"],
      Tester = jdata["主测人"],
      FinTime = jdata["实际完成时间"],
      
      Confirm1 = jdata["1-1"],
      Confirm2 = jdata["1-2"],
      Confirm3 = jdata["1-3"],
      Confirm4 = jdata["2-1"],
      Confirm5 = jdata["2-2"],
      Confirm6 = jdata["3-1"],
      Confirm7 = jdata["3-2"],
      Confirm8 = jdata["4"],
      Confirm9 = jdata["5-1"],
      Confirm10 = jdata["5-2"],
      Confirm11 = jdata["5-3"],
      Confirm12 = jdata["5-4"],
      Confirm13 = jdata["5-5"],
      Confirm14 = jdata["6-1"],
      Confirm15 = jdata["7-1"],
      Confirm16 = jdata["7-2"],
      Confirm17 = jdata["7-3"],
      Confirm18 = jdata["8-1"],
      Confirm19 = jdata["8-2"],
      Confirm20 = jdata["8-3"],
      Confirm21 = jdata["8-4"],
      Confirm22 = jdata["9-1"],
      Confirm23 = jdata["9-2"],
      Confirm24 = jdata["9-3"],
    )

    wordname = o_path + '.docx' 
    document.write(wordname)  # 创建新文件
    #convert(wordname, o_path + '.pdf')
    
    
    if platform.system() == 'Windows':
        convert(wordname, o_path + '.pdf')
    else:   
        os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")
  
if __name__ == '__main__':
    main(sys.argv[1:])
    
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test

# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test
# test# test
# test
# test
# test
# test
# test
# test
# test
# test
# test