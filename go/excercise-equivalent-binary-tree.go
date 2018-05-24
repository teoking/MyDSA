package main

import "golang.org/x/tour/tree"
import "fmt"

// This is my implementation for Go Tour's concurrency/8 excercise.
// Compare binary tree with Go is impressively simple.

// Walk walks the tree t sending all values
// from the tree to the channel ch.
func Walk(t *tree.Tree, ch chan int) {
	// inorder tranverse
	if t.Left != nil {
		Walk(t.Left, ch)
	}
	if t != nil {
		ch <- t.Value
	}
	if t.Right != nil {
		Walk(t.Right, ch)
	}
}

// Same determines whether the trees
// t1 and t2 contain the same values.
func Same(t1, t2 *tree.Tree) bool {
	//fmt.Println(t1)
	//fmt.Println(t2)
	ch1 := make(chan int)
	ch2 := make(chan int)
	go Walk(t1, ch1)
	go Walk(t2, ch2)
	for i := 0; i < 10; i++ {
		x := <-ch1
		y := <-ch2
		if x != y {
			return false
		}
	}
	return true
}

func main() {
	/*
		ch := make(chan int)
		go Walk(tree.New(1), ch)
		for i := 0; i < 10; i++ {
			fmt.Println(<-ch)
		}
	*/
	fmt.Println(Same(tree.New(1), tree.New(1)))
	fmt.Println(Same(tree.New(1), tree.New(2)))
}
