const AddToCart = document.getElementsByClassName('addProduct')
const cartSize = document.getElementById('badge');

for(let cart of AddToCart){
    cart.addEventListener("click", addItemToCart)
    cart.addEventListener("click", increaseCartSize);
}

async function addItemToCart(event){
    return await fetch('/add?prod_id=' +event.target.dataset.id)
}

function increaseCartSize(){
    let text = cartSize.textContent;
    let number = parseInt(text);
    cartSize.textContent=  (number+1).toString();
}