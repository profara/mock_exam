import {saveApplication} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";

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
            navigate("/valuta");
        }).catch(err => {
        errorNotification(
            err.code,
            err?.response.data.violations[0].error
        )
    })
}
