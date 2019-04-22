# Golang中unsafe.Pointer 和 uintptr指针操作
---
## 关于unsafe.Pointer和uintptr的几点说明：
- uintptr是一个整数类型
  - 即使uintptr变量仍然有效，由uintptr变量表示的地址处的数据也可能被GC回收
- unsafe.Pointer是一个指针类型
  - 但是unsafe.Pointer值不能被取消引用
  - 如果unsafe.Pointer变量仍然有效，则由unsafe.Pointer变量表示的地址处的数据不会被GC回收
- unsafe.Pointer是一个通用的指针类型，就像* int等

> 用指针修改数组和对象中的值。[代码参看test2.go](test2.go)
```go
package main

import (
	"fmt"
	"unsafe"
)

func main() {
	a := [4]int{0, 1, 2, 3}
	p1 := unsafe.Pointer(&a[1])
	p3 := unsafe.Pointer(uintptr(p1) + 2 * unsafe.Sizeof(a[0]))
	*(*int)(p3) = 6
	fmt.Println("a =", a) // a = [0 1 2 6]

	// ...

	type Person struct {
		name   string
		age    int
		gender bool
	}

	who := Person{"John", 30, true}
	pp := unsafe.Pointer(&who)
	pname := (*string)(unsafe.Pointer(uintptr(pp) + unsafe.Offsetof(who.name)))
	page := (*int)(unsafe.Pointer(uintptr(pp) + unsafe.Offsetof(who.age)))
	pgender := (*bool)(unsafe.Pointer(uintptr(pp) + unsafe.Offsetof(who.gender)))
	*pname = "Alice"
	*page = 28
	*pgender = false
	fmt.Println(who) // {Alice 28 false}
}
```
> 使用unsafe.Pointer可以对私有变量进行修改。[代码参看test3.go](test3.go)
```go
package main

import (
	"fmt"
	"unsafe"
)

type V struct {
	i int32
	j int64
}

func (this V) PutI()  {
	fmt.Printf("i=%d\n", this.i)
}

func (this V) PutJ()  {
	fmt.Printf("j=%d\n", this.j)
}

func main()  {
	v := new(V)
	pp := unsafe.Pointer(v)

	ii := (*int32)(pp)
	jj := (*int32)(unsafe.Pointer(uintptr(pp) + uintptr(unsafe.Sizeof(int64(0)))))
	*ii = 188
	*jj = 199
	v.PutI()
	v.PutJ()
}
```
