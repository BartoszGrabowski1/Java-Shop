const AddToCart = document.getElementsByClassName('addProduct')

for(let cart of AddToCart){
    cart.addEventListener("click", addItemToCart)
}

async function addItemToCart(event){
    return await fetch('/add?prod_id=' +event.target.dataset.id)
}