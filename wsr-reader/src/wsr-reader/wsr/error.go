package wsr

var errorMap map[int32]string = make(map[int32]string, 50)

//成功
const ERR_SUCC int32 = 0

//基础设施错误

const ERR_LOAD_LIB int32 = 1000
const ERR_INTERFACE int32 = 1002
const ERR_COM_PORT_NOTFOUND int32 = 1003

//业务错误
const ERR_UUID_LENGTH int32 = 2000
const ERR_LOAD_PSW_LEN int32 = 2001
const ERR_SET_PSW_LEN int32 = 2002

type wsrError struct {
	code int32
}

func newWsrError(code int32) error {
	return &wsrError{code: code}
}

func (this *wsrError) Error() string {
	if this.code >= 0 && this.code < 1000 {
		return "成功"
	}

	var ret string = "未知错误"

	switch this.code {
	case ERR_SUCC:
		ret = "成功"
	case ERR_LOAD_LIB:
		ret = "链接库加载失败"
	case ERR_INTERFACE:
		ret = "找不到对应的函数地址"
	case ERR_COM_PORT_NOTFOUND:
		ret = "找不到通讯端口"
	case ERR_UUID_LENGTH:
		ret = "写入UUID时, 长度错误"
	case ERR_LOAD_PSW_LEN:
		ret = "加载密码时, 密码长度错误"
	case ERR_SET_PSW_LEN:
		ret = "设置密码时, 密码长度错误"

	case -1:
		ret = "参数值不正确"

	case -10:
		ret = "(仅修改密码)装载密码失败"
	case -11:
		ret = "(仅修改密码)读数据失败"
	case -12:
		ret = "(仅修改密码)写数据失败"

	case -100:
		ret = "写串口失败"
	case -101:
		ret = "读串口失败"
	case -102:
		ret = "接收的数据无效"

	case -226:
		ret = "卡不存在/卡坏"
	case -227:
		ret = "防冲撞错误"
	case -228:
		ret = "锁定卡出错"
	case -229:
		ret = "传密钥出错"
	case -230:
		ret = "密码验证出错"
	case -231:
		ret = "读块数据出错"
	case -232:
		ret = "写块数据出错"
	case -233:
		ret = "复位 RC500 失败"
	case -234:
		ret = "钱包值调入缓冲区出错"
	case -235:
		ret = "保存缓冲区值中的钱包值出错"
	case -236:
		ret = "增值出错"
	case -238:
		ret = "减值出错"
	case -240:
		ret = "块值格式不对"
	case -241:
		ret = "块值不够减"
	case -242:
		ret = "值溢出"
	}

	return ret
}
