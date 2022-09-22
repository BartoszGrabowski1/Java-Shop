const AddToCart = document.getElementsByClassName('addProduct')
const RemoveToCart = document.getElementsByClassName('removeProduct')
console.log("dupa")
for(let cart of AddToCart){
    cart.addEventListener("click", addItemToCart)
}

async function addItemToCart(event){
    return await fetch('/add?prod_id=' +event.target.dataset.id)
}

for(let cart of RemoveToCart){
    cart.addEventListener("click", removeItemToCart)
}

async function removeItemToCart(event){
    return await fetch('/remove?prod_id=' +event.target.dataset.id)
}
