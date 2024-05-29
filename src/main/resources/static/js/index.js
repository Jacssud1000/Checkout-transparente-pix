document.addEventListener("DOMContentLoaded", function() {
    const mp = new MercadoPago("TEST-2ec1c00f-0ba9-4a39-bc94-445f8071faa2");

    async function getIdentificationTypes() {
        try {
            const identificationTypes = await mp.getIdentificationTypes();
            const identificationTypeElement =
                document.getElementById('form-checkout__identificationType');
            createSelectOptions(identificationTypeElement, identificationTypes);
        } catch (e) {
            return console.error('Error getting identificationTypes: ', e);
        }
    }

    async function submit(e) {
        e.preventDefault();
        const first_name = document.getElementById("form-checkout__payerFirstName").value;
        const last_name = document.getElementById("form-checkout__payerLastName").value;
        const email = document.getElementById("form-checkout__email").value;
        const type = document.getElementById("form-checkout__identificationType").value;
        const number = document.getElementById("form-checkout__identificationNumber").value;

        const producto = {
            transactionAmount: 100,
            description: "Titulo do produto",
            paymentMethodId: "pix",
            payerDto: {
                email: email,
                first_name,
                last_name,
                identification: {
                    type,
                    number
                }
            }
        }

        try {
            const response = await fetch("http://localhost:8080/v1/payments", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(producto)
            });

            if (!response.ok) {
                throw new Error('Error en la solicitud de pago');
            }

            const paymentData = await response.json();

            document.getElementById('pix-link').href = paymentData.ticket_url;
            document.getElementById('qr-code-image').src = `data:image/jpeg;base64,${paymentData.qrCodeBase64}`;
            document.getElementById('copiar').value = paymentData.qrCode;

            document.getElementById('payment-result').style.display = 'block';

        } catch (error) {
            console.error('Error al realizar el pago:', error);
        }
    }

    function createSelectOptions(elem, options, labelsAndKeys = {label: "name", value: "id"}) {
        const {label, value} = labelsAndKeys;

        elem.options.length = 0;

        const tempOptions = document.createDocumentFragment();

        options.forEach(option => {
            const optValue = option[value];
            const optLabel = option[label];

            const opt = document.createElement('option');
            opt.value = optValue;
            opt.textContent = optLabel;

            tempOptions.appendChild(opt);
        });

        elem.appendChild(tempOptions);
    }

    getIdentificationTypes();

    const button = document.getElementById("btPayment");
    button.addEventListener("click", submit);
});
