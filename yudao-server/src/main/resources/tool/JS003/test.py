
from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import sys
import getopt

import platform
from docx2pdf import convert
import os
from mailmerge import MailMerge  # 引用邮件处理模块


#-t 'C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS003\NST－04－JS003－2011－委托测试软件功能列表.docx' -i 'C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS003\data.json' -o 'C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS003\output'

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

    _Fun1 = ""
    _Fun2 = ""
    _Fun3 = ""
    _Fun4 = ""
    _Fun5 = ""
    
    _Fun11 = ""
    _Fun12 = ""
    _Fun13 = ""
    _Fun14 = ""
    _Fun15 = ""
    _Fun21 = ""
    _Fun22 = ""
    _Fun23 = ""
    _Fun24 = ""
    _Fun31 = ""
    _Fun32 = ""
    _Fun33 = ""
    _Fun34 = ""
    _Fun35 = ""
    _Fun36 = ""
    _Fun37 = ""
    _Fun41 = ""
    _Fun42 = ""
    _Fun43 = ""
    _Fun44 = ""
    _Fun51 = ""
    _Fun52 = ""
    _Fun53 = ""
    _Fun54 = ""
    
    _Des11 = ""
    _Des12 = ""
    _Des13 = ""
    _Des14 = ""
    _Des15 = ""
    _Des21 = ""
    _Des22 = ""
    _Des23 = ""
    _Des24 = ""
    _Des31 = ""
    _Des32 = ""
    _Des33 = ""
    _Des34 = ""
    _Des35 = ""
    _Des36 = ""
    _Des37 = ""
    _Des41 = ""
    _Des42 = ""
    _Des43 = ""
    _Des44 = ""
    _Des51 = ""
    _Des52 = ""
    _Des53 = ""
    _Des54 = ""

    if len(jdata["function"]) >= 1:
          _Fun1 = jdata["function"][0]["功能名称"]
          if len(jdata["function"][0]["子功能列表"]) >= 1:
                _Fun11 = jdata["function"][0]["子功能列表"][0]["子功能名称"]
                _Des11 = jdata["function"][0]["子功能列表"][0]["子功能说明"]
          if len(jdata["function"][0]["子功能列表"]) >= 2:
                _Fun12 = jdata["function"][0]["子功能列表"][1]["子功能名称"]
                _Des12 = jdata["function"][0]["子功能列表"][1]["子功能说明"]
          if len(jdata["function"][0]["子功能列表"]) >= 3:
                _Fun13 = jdata["function"][0]["子功能列表"][2]["子功能名称"]
                _Des13 = jdata["function"][0]["子功能列表"][2]["子功能说明"]
          if len(jdata["function"][0]["子功能列表"]) >= 4:
                _Fun14 = jdata["function"][0]["子功能列表"][3]["子功能名称"]
                _Des14 = jdata["function"][0]["子功能列表"][3]["子功能说明"]
          if len(jdata["function"][0]["子功能列表"]) >= 5:
                _Fun15 = jdata["function"][0]["子功能列表"][4]["子功能名称"]
                _Des15 = jdata["function"][0]["子功能列表"][4]["子功能说明"]    
    if len(jdata["function"]) >= 2:
          _Fun2 = jdata["function"][1]["功能名称"]
          if len(jdata["function"][1]["子功能列表"]) >= 1:
                _Fun21 = jdata["function"][1]["子功能列表"][0]["子功能名称"]
                _Des21 = jdata["function"][1]["子功能列表"][0]["子功能说明"]
          if len(jdata["function"][1]["子功能列表"]) >= 2:
                _Fun22 = jdata["function"][1]["子功能列表"][1]["子功能名称"]
                _Des22 = jdata["function"][1]["子功能列表"][1]["子功能说明"]
          if len(jdata["function"][1]["子功能列表"]) >= 3:
                _Fun23 = jdata["function"][1]["子功能列表"][2]["子功能名称"]
                _Des23 = jdata["function"][1]["子功能列表"][2]["子功能说明"]
          if len(jdata["function"][1]["子功能列表"]) >= 4:
                _Fun24 = jdata["function"][1]["子功能列表"][3]["子功能名称"]
                _Des24 = jdata["function"][1]["子功能列表"][3]["子功能说明"]
    if len(jdata["function"]) >= 3:
          _Fun3 = jdata["function"][2]["功能名称"]
          if len(jdata["function"][2]["子功能列表"]) >= 1:
                _Fun31 = jdata["function"][2]["子功能列表"][0]["子功能名称"]
                _Des31 = jdata["function"][2]["子功能列表"][0]["子功能说明"]
          if len(jdata["function"][2]["子功能列表"]) >= 2:
                _Fun32 = jdata["function"][2]["子功能列表"][1]["子功能名称"]
                _Des32 = jdata["function"][2]["子功能列表"][1]["子功能说明"]
          if len(jdata["function"][2]["子功能列表"]) >= 3:
                _Fun33 = jdata["function"][2]["子功能列表"][2]["子功能名称"]
                _Des33 = jdata["function"][2]["子功能列表"][2]["子功能说明"]
          if len(jdata["function"][2]["子功能列表"]) >= 4:
                _Fun34 = jdata["function"][2]["子功能列表"][3]["子功能名称"]
                _Des34 = jdata["function"][2]["子功能列表"][3]["子功能说明"]
          if len(jdata["function"][2]["子功能列表"]) >= 5:
                _Fun35 = jdata["function"][2]["子功能列表"][4]["子功能名称"]
                _Des35 = jdata["function"][2]["子功能列表"][4]["子功能说明"]  
          if len(jdata["function"][2]["子功能列表"]) >= 6:
                _Fun36 = jdata["function"][2]["子功能列表"][5]["子功能名称"]
                _Des36 = jdata["function"][2]["子功能列表"][5]["子功能说明"]
          if len(jdata["function"][2]["子功能列表"]) >= 7:
                _Fun37 = jdata["function"][2]["子功能列表"][6]["子功能名称"]
                _Des37 = jdata["function"][2]["子功能列表"][6]["子功能说明"]                  
    if len(jdata["function"]) >= 4:
          _Fun4 = jdata["function"][3]["功能名称"]
          if len(jdata["function"][3]["子功能列表"]) >= 1:
                _Fun41 = jdata["function"][3]["子功能列表"][0]["子功能名称"]
                _Des41 = jdata["function"][3]["子功能列表"][0]["子功能说明"]
          if len(jdata["function"][3]["子功能列表"]) >= 2:
                _Fun42 = jdata["function"][3]["子功能列表"][1]["子功能名称"]
                _Des42 = jdata["function"][3]["子功能列表"][1]["子功能说明"]
          if len(jdata["function"][3]["子功能列表"]) >= 3:
                _Fun43 = jdata["function"][3]["子功能列表"][2]["子功能名称"]
                _Des43 = jdata["function"][3]["子功能列表"][2]["子功能说明"]
          if len(jdata["function"][3]["子功能列表"]) >= 4:
                _Fun44 = jdata["function"][3]["子功能列表"][3]["子功能名称"]
                _Des44 = jdata["function"][3]["子功能列表"][3]["子功能说明"]
    if len(jdata["function"]) >= 5:
          _Fun5 = jdata["function"][4]["功能名称"]
          if len(jdata["function"][4]["子功能列表"]) >= 1:
                _Fun51 = jdata["function"][4]["子功能列表"][0]["子功能名称"]
                _Des51 = jdata["function"][4]["子功能列表"][0]["子功能说明"]
          if len(jdata["function"][4]["子功能列表"]) >= 2:
                _Fun52 = jdata["function"][4]["子功能列表"][1]["子功能名称"]
                _Des52 = jdata["function"][4]["子功能列表"][1]["子功能说明"]
          if len(jdata["function"][4]["子功能列表"]) >= 3:
                _Fun53 = jdata["function"][4]["子功能列表"][2]["子功能名称"]
                _Des53 = jdata["function"][4]["子功能列表"][2]["子功能说明"]
          if len(jdata["function"][4]["子功能列表"]) >= 4:
                _Fun54 = jdata["function"][4]["子功能列表"][3]["子功能名称"]
                _Des54 = jdata["function"][4]["子功能列表"][3]["子功能说明"]
    
    document.merge(
      SoftName = jdata["软件名称"],
      Version = jdata["版本号"],
      
      Fun1 = _Fun1,
      Fun2 = _Fun2,
      Fun3 = _Fun3,
      Fun4 = _Fun4,
      Fun5 = _Fun5,
      
      Fun11 = _Fun11,
      Fun12 = _Fun12,
      Fun13 = _Fun13,
      Fun14 = _Fun14,
      Fun15 = _Fun15,
      Fun21 = _Fun21,
      Fun22 = _Fun22,
      Fun23 = _Fun23,
      Fun24 = _Fun24,
      Fun31 = _Fun31,
      Fun32 = _Fun32,
      Fun33 = _Fun33,
      Fun34 = _Fun34,
      Fun35 = _Fun35,
      Fun36 = _Fun36,
      Fun37 = _Fun37,
      Fun41 = _Fun41,
      Fun42 = _Fun42,
      Fun43 = _Fun43,
      Fun44 = _Fun44,
      Fun51 = _Fun51,
      Fun52 = _Fun52,
      Fun53 = _Fun53,
      Fun54 = _Fun54,
      
      Des11 = _Des11,
      Des12 = _Des12,
      Des13 = _Des13,
      Des14 = _Des14,
      Des15 = _Des15,
      Des21 = _Des21,
      Des22 = _Des22,
      Des23 = _Des23,
      Des24 = _Des24,
      Des31 = _Des31,
      Des32 = _Des32,
      Des33 = _Des33,
      Des34 = _Des34,
      Des35 = _Des35,
      Des36 = _Des36,
      Des37 = _Des37,
      Des41 = _Des41,
      Des42 = _Des42,
      Des43 = _Des43,
      Des44 = _Des44,
      Des51 = _Des51,
      Des52 = _Des52,
      Des53 = _Des53,
      Des54 = _Des54,
    )
    wordname = o_path + '.docx' 
    document.write(wordname)  # 创建新文件
    #convert(wordname, o_path + '.pdf')
    #os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")
    
    if platform.system() == "Windows":
          convert(wordname, o_path + '.pdf')
    else:
          os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")
    
if __name__ == '__main__':
    main(sys.argv[1:])
