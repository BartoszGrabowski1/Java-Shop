const AddToCart = document.getElementsByClassName('addProduct')

for(let cart of AddToCart){
    cart.addEventListener("click", addItemToCart)
}


// const getProductId = async () => {
//     const response = await fetch('/cart/add?prod_name=' + product);
//     return await response.json()
// };
async function addItemToCart(event){
    return await fetch('/cart/add?prod_id=' +event.target.dataset.id)
}