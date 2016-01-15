package wsr

import (
	"fmt"
	"syscall"
	"unsafe"
)

var (
	initPswA string = "\xFF\xFF\xFF\xFF\xFF\xFF"
	initPswB string = "\xFF\xFF\xFF\xFF\xFF\xFF"
)

type Wsr struct {
	hWsr          syscall.Handle
	port          uintptr
	uuidBlk       uintptr
	uuidLen       uintptr
	passwdLen     int
	accessCodeLen int
	pswSector     int
}

func NewWsr() (*Wsr, error) {
	hWsr, err := syscall.LoadLibrary("WSR.DLL")
	if err != nil {
		return nil, newWsrError(ERR_LOAD_LIB)
	}

	return &Wsr{
		hWsr:          hWsr,
		port:          0,
		uuidBlk:       6,
		uuidLen:       40,
		passwdLen:     6,
		accessCodeLen: 4,
		pswSector:     0,
	}, nil
}

func (this *Wsr) Destroy() error {
	return syscall.FreeLibrary(this.hWsr)
}

//find the com port and open it
func (this *Wsr) OpenPort() error {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_openPort")
	if err != nil {
		return newWsrError(ERR_INTERFACE)
	}

	//find the port
	for port := 0; port <= 255; port++ {
		r1, _, _ := syscall.Syscall(uintptr(proc), 1, uintptr(port), 0, 0)
		if int32(r1) >= 0 {
			this.port = uintptr(port)
			fmt.Println("open port ", port, " succ")
			return nil
		}
	}

	return newWsrError(ERR_COM_PORT_NOTFOUND)
}

//close the com port
func (this *Wsr) ClosePort() error {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_closePort")
	if err != nil {
		return newWsrError(ERR_INTERFACE)
	}
	r1, _, _ := syscall.Syscall(uintptr(proc), 1, this.port, 0, 0)
	if int32(r1) >= 0 {
		return nil
	}

	//关闭端口失败也算是成功
	return newWsrError(int32(r1))
}

func (this *Wsr) Beep() error {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_beep")
	if err != nil {
		return newWsrError(ERR_INTERFACE)
	}

	r1, _, _ := syscall.Syscall(uintptr(proc), 1, this.port, 0, 0)
	if int32(r1) >= 0 {
		return nil
	}

	return newWsrError(int32(r1))
}

func (this *Wsr) GetCardNo() (string, error) {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_getCardNo_String")
	if err != nil {
		return "", newWsrError(ERR_INTERFACE)
	}

	cardNo := make([]byte, 11)
	r1, _, _ := syscall.Syscall(uintptr(proc), 2, this.port, uintptr(unsafe.Pointer(&cardNo[0])), 0)
	if int32(r1) >= 0 {
		return string(cardNo), nil
	}

	return "", newWsrError(int32(r1))
}

func (this *Wsr) ReadUuid() (string, error) {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_readData")
	if err != nil {
		return "", newWsrError(ERR_INTERFACE)
	}

	uuid := make([]byte, this.uuidLen)
	r1, _, _ := syscall.Syscall6(uintptr(proc), 4,
		this.port, this.uuidBlk, uintptr(unsafe.Pointer(&uuid[0])), uintptr(this.uuidLen),
		0, 0)
	if int32(r1) >= 0 {
		return string(uuid), nil
	}

	return "", newWsrError(int32(r1))
}

func (this *Wsr) WriteUuid(uuid string) error {
	if uintptr(len(uuid)) != this.uuidLen {
		return newWsrError(ERR_UUID_LENGTH)
	}

	proc, err := syscall.GetProcAddress(this.hWsr, "ws_writeData")
	if err != nil {
		return newWsrError(ERR_INTERFACE)
	}

	uuidBytes := []byte(uuid)
	r1, _, _ := syscall.Syscall6(uintptr(proc), 4,
		this.port, this.uuidBlk, uintptr(unsafe.Pointer(&uuidBytes[0])), this.uuidLen,
		0, 0)
	if int32(r1) >= 0 {
		return nil
	}

	return newWsrError(int32(r1))
}

//load passwd.
//pswType: 0-Password A(default), 1-Password B
func (this *Wsr) LoadPsw(passwd string, pswType int32) error {
	if len(passwd) != this.passwdLen {
		return newWsrError(ERR_LOAD_PSW_LEN)
	}

	proc, err := syscall.GetProcAddress(this.hWsr, "ws_loadKey")
	if err != nil {
		return newWsrError(ERR_INTERFACE)
	}

	passwdBytes := []byte(passwd)
	r1, _, _ := syscall.Syscall(uintptr(proc), 3,
		this.port, uintptr(unsafe.Pointer(&passwdBytes[0])), uintptr(pswType),
	)
	if int32(r1) >= 0 {
		return nil
	}

	return newWsrError(int32(r1))
}

func (this *Wsr) SetPsw(pswA string, accessCode string, pswB string) error {
	if len(pswA) != this.passwdLen || len(pswB) != this.passwdLen || len(accessCode) != this.accessCodeLen {
		return newWsrError(ERR_SET_PSW_LEN)
	}

	proc, err := syscall.GetProcAddress(this.hWsr, "ws_changeKeyA")
	if err != nil {
		return newWsrError(ERR_INTERFACE)
	}

	paBytes := []byte(pswA)
	pbBytes := []byte(pswB)
	pacBytes := []byte(accessCode)
	r1, _, _ := syscall.Syscall6(uintptr(proc), 5,
		uintptr(this.port),
		uintptr(this.pswSector),
		uintptr(unsafe.Pointer(&paBytes[0])),
		uintptr(unsafe.Pointer(&pacBytes[0])),
		uintptr(unsafe.Pointer(&pbBytes[0])),
		0)
	if int32(r1) >= 0 {
		return nil
	}

	return newWsrError(int32(r1))
}
