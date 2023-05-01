const imagesPreview = document.createElement('div');
imagesPreview.classList.add('images-preview');

const imagesInput = document.getElementById('images');
imagesInput.parentNode.insertBefore(imagesPreview, imagesInput.nextSibling);

imagesInput.addEventListener('change', function() {
    imagesPreview.innerHTML = ''; // Clear previous preview
    for (let i = 0; i < this.files.length; i++) {
        const file = this.files[i];
        if (file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function() {
                const img = document.createElement('img');
                img.src = reader.result;
                imagesPreview.appendChild(img);
            }
        }
    }
});




const hashtagInput = document.getElementById('hashtag');
hashtagInput.addEventListener('focus', addHashtagSymbol);
hashtagInput.addEventListener('keydown', handleHashtagInput);

function addHashtagSymbol() {
    if (!hashtagInput.value.startsWith('#')) {
        hashtagInput.value = '#' + hashtagInput.value;
    }
}

function handleHashtagInput(event) {
    const message = hashtagInput.parentNode.querySelector('.error-message');
    if (isInValidKey(event.key)) {
        if (!message) {
            const newMessage = document.createElement('div');
            newMessage.classList.add('error-message');
            newMessage.textContent = '해시태그에 특수문자는 입력할 수 없습니다.';
            newMessage.style.color = 'red';
            hashtagInput.parentNode.append(newMessage, hashtagInput.nextSibling);
            event.preventDefault();
        }
        event.preventDefault();
    } else if (event.keyCode === 32 ) {
            hashtagInput.value += ' #';
        event.preventDefault();
    } else if (event.key === '#' && hashtagInput.value.endsWith('#')) {
        const newMessage = document.createElement('div');
        newMessage.classList.add('error-message');
        newMessage.textContent = '해시태그는 중복할 수 없습니다.';
        newMessage.style.color = 'red';
        hashtagInput.parentNode.append(newMessage, hashtagInput.nextSibling);
        event.preventDefault();
    } else if (event.key === '#' ) {
        hashtagInput.value += ' #';
        event.preventDefault();


    } else {
        if (message) {
            message.remove();
        }
    }
}

function isInValidKey(key) {
    const notAllowedKeys = ['Delete', 'ArrowUp', 'ArrowDown', 'Control', 'Alt', 'Enter'];
    return /^[!@$%^&*()_+,.\-=/<>?;':"|}{\[\]\\]+$/.test(key) || notAllowedKeys.includes(key);
}

