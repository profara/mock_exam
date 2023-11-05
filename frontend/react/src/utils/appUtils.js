import {saveApplication, savePayment} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";
import {CREDITOR_ACCOUNT, PAYMENT_PURPOSE, REFERENCE_NUMBER} from "../components/payslip/config/constants.js";

export function getCurrentDateInSerbiaTimeZone() {
    const now = new Date();

    const serbiaDate = new Intl.DateTimeFormat('en-US', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        timeZone: 'Europe/Belgrade'
    }).format(now);

    const [month, day, year] = serbiaDate.split('/');
    const isoString = `${year}-${month}-${day}`;

    return isoString;
}

export const createApplication = (candidate, serbiaDate, selectedCards, setApplication, navigate) => {
    const application = {
        applicationDate: serbiaDate,
        privileged: candidate.attendedPreparation,
        candidate: candidate.id,
        appointmentIds: [...selectedCards]
    }
    saveApplication(application)
        .then(res => {
            successNotification(
                "Uspesno sacuvana prijava",
                ""
            )
            setApplication(res.data);

            const payment = {
                referenceNumber: `${REFERENCE_NUMBER}-${candidate.id}`,
                creditorAccount: CREDITOR_ACCOUNT,
                paymentPurpose: PAYMENT_PURPOSE,
                applicationId: res.data.id,
            }
            savePayment(payment)
                .then(res => {
                    successNotification(
                        "Uspesno kreirana uplatnica"
                    )
                    navigate("/uplatnica", { state: {payment: res.data, application: application}});
                }).catch(err => {
                errorNotification(
                    err.code,
                    err?.response.data.message
                )
            })
            navigate("/uplatnica")
        }).catch(err => {
        errorNotification(
            err.code,
            err?.response.data.message
        )
    })
}
