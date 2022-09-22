const AddToCart = document.getElementsByClassName('addProduct')
const IncreaseQuantityButton = document.getElementsByClassName('increaseQuantity');
const DecreaseQuantityBytton = document.getElementsByClassName('decreaseQuantity');
const cartSize = document.getElementById('badge');
const RemoveToCart = document.getElementsByClassName('removeProduct')
const orderSummary = document.getElementById('final-value');


for(let button of IncreaseQuantityButton){
    button.addEventListener("click",addItemToCart)
    button.addEventListener("click", increaseProductQuantity)
    button.addEventListener("click",increaseCartSize)
}
for(let button of DecreaseQuantityBytton){
    button.addEventListener("click", removeItemToCart)
    button.addEventListener("click", decreaseProductQuantity)
    button.addEventListener("click",decreaseCartSize)
}
for(let cart of AddToCart){
    cart.addEventListener("click", addItemToCart)
    cart.addEventListener("click", increaseCartSize)
}

async function addItemToCart(event){
    return await fetch('/add?prod_id=' +event.target.dataset.id)
}

function increaseProductQuantity(event){
    let productQuantityDiv = document.getElementById("count_"+event.target.dataset.id);
    let productValueDiv = document.getElementById("value_"+event.target.dataset.id);
    let productQuantity = parseInt(productQuantityDiv.textContent);
    let productValue = parseInt(productValueDiv.textContent);
    productValueDiv.textContent=(productValue+productValue/productQuantity).toString();
    productQuantityDiv.textContent=(productQuantity+1).toString();
    orderSummary.textContent = (parseInt(orderSummary.textContent)+productValue/productQuantity).toString() + " USD";
}

function decreaseProductQuantity(event){
    let productQuantityDiv = document.getElementById("count_"+event.target.dataset.id);
    let productValueDiv = document.getElementById("value_"+event.target.dataset.id);
    let productQuantity = parseInt(productQuantityDiv.textContent);
    let productValue = parseInt(productValueDiv.textContent);
    if(productQuantity-1 <=0){
        deleteProductFromCart(event)
        if((parseInt(orderSummary.textContent) - productValue / productQuantity) <=0){
            document.getElementById("cartWithProducts").remove();
            document.getElementById("orderValue").remove();
            document.getElementById("container").innerText ="Pusty Koszyk";
        }
    }else {
        productValueDiv.textContent=(productValue-productValue/productQuantity).toString();
        productQuantityDiv.textContent = (productQuantity - 1).toString();
    }
    orderSummary.textContent = (parseInt(orderSummary.textContent) - productValue / productQuantity).toString() + " USD";
}
function deleteProductFromCart(event){
    let divToDelete = document.getElementById("product_"+event.target.dataset.id)
    divToDelete.remove();
}

function increaseCartSize() {
    let text = cartSize.textContent;
    let number = parseInt(text);
    cartSize.textContent = (number + 1).toString();
    sessionStorage.setItem("cartSize",cartSize.textContent = (number + 1).toString())
}
function decreaseCartSize() {
    let text = cartSize.textContent;
    let number = parseInt(text);
    cartSize.textContent = (number - 1).toString();
    sessionStorage.setItem("cartSize",cartSize.textContent = (number - 1).toString())
}
function loadCartSize(){
    cartSize.textContent = sessionStorage.getItem("cartSize");
}
for(let cart of RemoveToCart){
    cart.addEventListener("click", removeItemToCart)
}

async function removeItemToCart(event){
    return await fetch('/remove?prod_id=' +event.target.dataset.id)
}

