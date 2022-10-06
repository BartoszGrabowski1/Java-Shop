
const SaveOrder = document.getElementsByClassName('btn btn-primary btn-block free-button');

for(let cart of SaveOrder){
    cart.addEventListener("click", addItemToCart);

}

async function addItemToCart(event) {
    return await fetch('/save_order?name=' + event.target.dataset.id, {method: "POST"});
}