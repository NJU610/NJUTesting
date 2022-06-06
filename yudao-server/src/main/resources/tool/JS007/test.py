from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import platform
import getopt
import sys
import xlwt
from docx2pdf import convert
import os
from mailmerge import MailMerge  # 引用邮件处理模块
# -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS007\data.json" -t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS007\NST－04－JS007－2011－软件测试报告.docx" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS007\output"
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

        _TestBasis1 = ""
        _TestBasis2 = ""
        _TestBasis3 = ""
        if len(jdata["测试依据"]) >= 1:
            _TestBasis1 = jdata["测试依据"][0]["basis"]
        if len(jdata["测试依据"]) >= 2:
            _TestBasis2 = jdata["测试依据"][1]["basis"]
        if len(jdata["测试依据"]) >= 3:
            _TestBasis3 = jdata["测试依据"][2]["basis"]
        
        _Refer1 = ""
        _Refer2 = ""
        _Refer3 = ""
        _Refer4 = ""
        if len(jdata["参考资料"]) >= 1:
            _Refer1 = jdata["参考资料"][0]["refer"]
        if len(jdata["参考资料"]) >= 2:
            _Refer2 = jdata["参考资料"][1]["refer"]
        if len(jdata["参考资料"]) >= 3:
            _Refer3 = jdata["参考资料"][2]["refer"]
        if len(jdata["参考资料"]) >= 4:
            _Refer3 = jdata["参考资料"][3]["refer"]
            
        _Fun1 = ""    
        _Fun2 = ""
        _Fun3 = ""
        _FunRequirement1 = ""
        _FunRequirement2 = ""
        _FunRequirement3 = ""
        _FunRes1 = ""
        _FunRes2 = ""
        _FunRes3 = ""
        if len(jdata["功能性测试"]) >= 1:
            _Fun1 = jdata["功能性测试"][0]["module"]
            _FunRequirement1 = jdata["功能性测试"][0]["require"]
            _FunRes1 = jdata["功能性测试"][0]["result"]
        if len(jdata["功能性测试"]) >= 2:
            _Fun2 = jdata["功能性测试"][1]["module"]
            _FunRequirement2 = jdata["功能性测试"][1]["require"]
            _FunRes2 = jdata["功能性测试"][1]["result"]
        if len(jdata["功能性测试"]) >= 3:
            _Fun3 = jdata["功能性测试"][2]["module"]
            _FunRequirement3 = jdata["功能性测试"][2]["require"]
            _FunRes3 = jdata["功能性测试"][2]["result"]

        #效率测试
        _TestFea1 = ""
        _TestDes1 = ""
        _TestRes1 = ""
        if len(jdata["效率测试"]) >= 1:
            _TestFea1 = jdata["效率测试"][0]["module"]
            _TestDes1 = jdata["效率测试"][0]["require"]
            _TestRes1 = jdata["效率测试"][0]["result"]
            
        #可移植性测试
        _TestFea2 = ""
        _TestDes2 = ""
        _TestRes2 = ""
        if len(jdata["可移植性测试"]) >= 1:
            _TestFea2 = jdata["可移植性测试"][0]["module"]
            _TestDes2 = jdata["可移植性测试"][0]["require"]
            _TestRes2 = jdata["可移植性测试"][0]["result"]
            
        #易用性测试
        _TestFea3 = ""
        _TestFea4 = ""
        _TestFea5 = ""
        _TestDes3 = ""
        _TestDes4 = ""
        _TestDes5 = ""
        _TestRes3 = ""
        _TestRes4 = ""
        _TestRes5 = ""
        if len(jdata["易用性测试"]) >= 1:
            _TestFea3 = jdata["易用性测试"][0]["module"]
            _TestDes3 = jdata["易用性测试"][0]["require"]
            _TestRes3 = jdata["易用性测试"][0]["result"]
        if len(jdata["易用性测试"]) >= 2:
            _TestFea4 = jdata["易用性测试"][1]["module"]
            _TestDes4 = jdata["易用性测试"][1]["require"]
            _TestRes4 = jdata["易用性测试"][1]["result"]
        if len(jdata["易用性测试"]) >= 3:
            _TestFea5 = jdata["易用性测试"][2]["module"]
            _TestDes5 = jdata["易用性测试"][2]["require"]
            _TestRes5 = jdata["易用性测试"][2]["result"]
        
        #可靠性测试
        _TestFea6 = ""
        _TestFea7 = ""
        _TestDes6 = ""
        _TestDes7 = ""
        _TestRes6 = ""
        _TestRes7 = ""
        if len(jdata["可靠性测试"]) >= 1:
            _TestFea6 = jdata["可靠性测试"][0]["module"]
            _TestDes6 = jdata["可靠性测试"][0]["require"]
            _TestRes6 = jdata["可靠性测试"][0]["result"]
        if len(jdata["可靠性测试"]) >= 2:
            _TestFea7 = jdata["可靠性测试"][1]["module"]
            _TestDes7 = jdata["可靠性测试"][1]["require"]
            _TestRes7 = jdata["可靠性测试"][1]["result"]
            
        #可维护性测试
        _TestFea8 = ""
        _TestFea9 = ""
        _TestDes8 = ""
        _TestDes9 = ""
        _TestRes8 = ""
        _TestRes9 = ""
        if len(jdata["可维护性测试"]) >= 1:
            _TestFea8 = jdata["可维护性测试"][0]["module"]
            _TestDes8 = jdata["可维护性测试"][0]["require"]
            _TestRes8 = jdata["可维护性测试"][0]["result"]
        if len(jdata["可维护性测试"]) >= 2:
            _TestFea9 = jdata["可维护性测试"][1]["module"]
            _TestDes9 = jdata["可维护性测试"][1]["require"]
            _TestRes9 = jdata["可维护性测试"][1]["result"]

        document.merge(
            SoftName = jdata["软件名称_1"],
            Version = jdata["版本号_1"],
            Delegation = jdata["委托单位_1"],
            TestType = jdata["测试类别_1"],
            Date = jdata["报告日期_1"],
            Delegation1 = jdata["委托单位"],
            ProjNum = jdata["项目编号"],
            SampleName = jdata["样品名称"],
            SampleVer = jdata["版本/型号"],
            SampleDate = jdata["来样日期"],
            TestType1 = jdata["测试类型"],
            TestBeginDate = jdata["测试时间"][0],
            TestEndTime = jdata["测试时间"][1],
            SampleStatus = jdata["样品状态"],
            
            TestBasis1 = _TestBasis1,
            TestBasis2 = _TestBasis2,
            TestBasis3 = _TestBasis3,
            
            SampleList = jdata["样品清单"],
            TestResult = jdata["测试结论"],
            
            Tester = jdata["主测人"],
            TestDate = jdata["主测_日期"],
            Assesser = jdata["审核人"],
            AssessDate = jdata["审核_日期"],
            Approver = jdata["批准人"],
            ApproveDate = jdata["批准_日期"],
            DelTel = jdata["电话"],
            DelFax = jdata["传真"],
            DelAddr = jdata["地址"],
            DelPost = jdata["邮编"],
            DelCom = jdata["联系人"],
            DelEmail = jdata["E-mail"],
        
            HardType = jdata["硬件类别"],
            HardName = jdata["硬件名称"],
            HardSetting = jdata["配置"],
            HardNum = jdata["数量"],
            
            OS = jdata["软件环境"][0]["name"],
            OSVer = jdata["软件环境"][0]["version"],
            Soft1 = jdata["软件环境"][1]["name"],
            Soft1Ver = jdata["软件环境"][1]["version"],
            Soft2 = "",
            SoftVer = "",
            Soft3 = "",
            Soft3Ver = "",
            Aux = jdata["软件环境"][2]["name"],
            AuxVer = jdata["软件环境"][2]["version"],
            Dev = jdata["软件环境"][3]["name"],
            DevVer = jdata["软件环境"][3]["version"],
            TestedSample = jdata["软件环境"][4]["name"],
            TestedSampleVer = jdata["软件环境"][4]["version"],
            NetEnv = jdata["网络环境"],
            
            RefBg = jdata["测试依据"][0]["basis"],
            
            Refer1 = _Refer1,
            Refer2 = _Refer2,
            Refer3 = _Refer3,
            Refer4 = _Refer4,
            
            Fun1 = _Fun1,
            Fun2 = _Fun2,
            Fun3 = _Fun3,
            FunRequirement1 = _FunRequirement1,
            FunRequirement2 = _FunRequirement2,
            FunRequirement3 = _FunRequirement3,
            FunRes1 = _FunRes1,
            FunRes2 = _FunRes2,
            FunRes3 = _FunRes3,
            
            #效率测试
            TestFea1 = _TestFea1,
            TestDes1 = _TestDes1,
            TestRes1 = _TestRes1,
            
            #可移植性测试
            TestFea2 = _TestFea2,
            TestDes2 = _TestDes2,
            TestRes2 = _TestRes2,
            
            #易用性测试
            TestFea3 = _TestFea3,
            TestFea4 = _TestFea4,
            TestFea5 = _TestFea5,
            TestDes3 = _TestDes3,
            TestDes4 = _TestDes4,
            TestDes5 = _TestDes5,
            TestRes3 = _TestRes3,
            TestRes4 = _TestRes4,
            TestRes5 = _TestRes5,
            
            TestFea6 = _TestFea6,
            TestDes6 = _TestDes6,
            TestRes6 = _TestRes6,
            TestFea7 = _TestFea7,
            TestDes7 = _TestDes7,
            TestRes7 = _TestRes7,
            TestFea8 = _TestFea8,
            TestDes8 = _TestDes8,
            TestRes8 = _TestRes8,
            TestFea9 = _TestFea9,
            TestDes9 = _TestDes9,
            TestRes9 = _TestRes9,
            
            Record = jdata["测试执行记录"]
        )
        wordname = o_path + '.docx' 
        document.write(wordname)  # 创建新文件
        
        if platform.system() == 'Windows':
            convert(wordname, o_path + '.pdf')
        else:   
            os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")

      

if __name__ == '__main__':
    main(sys.argv[1:])
