import {Flex, Spinner, VStack} from "@chakra-ui/react";
import CandidateCard from "./CandidateCard.jsx";
import {useEffect, useState} from "react";
import {getCandidates} from "../../services/client.js";
import CandidatesHeader from "./CandidatesHeader.jsx";
import Simple from "../shared/NavBar.jsx";

const CandidateList = () => {
    const [candidates, setCandidates] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getCandidates()
            .then(res => {
                console.log(res)
                setCandidates(res.data.content);
            }).catch(err => {
            console.error(err)
        }).finally(() => {
            setLoading(false);
        })

    }, [])

    if (loading) {
        return <Spinner
            thickness='4px'
            speed='0.65s'
            emptyColor='gray.200'
            color='blue.500'
            size='xl'
        />
    }

    return (
        <Simple>
            <Flex direction="column" w="100%">
                <CandidatesHeader/>
                {candidates.map((candidate, index) => (
                    <CandidateCard
                        key={candidate.id}
                        candidate={candidate}
                        isOdd={index % 2 !== 0}
                        rowNum={index + 1}
                    />
                ))}
            </Flex>
        </Simple>
    );
};

export default CandidateList;