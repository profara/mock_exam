import {Box, Button, Flex, Spinner} from "@chakra-ui/react";
import CandidateCard from "./CandidateCard.jsx";
import {useEffect, useState} from "react";
import {getCandidates} from "../../services/client.js";
import CandidatesHeader from "./CandidatesHeader.jsx";
import Simple from "../shared/NavBar.jsx";

const CandidateList = () => {
    const [candidates, setCandidates] = useState([]);
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(0);
    const pageSize = 20;

    const fetchCandidates = (page) => {
        getCandidates(page)
            .then(res => {
                setCandidates(res.data.content);
            }).catch(err => {
            console.error(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchCandidates(page)
    }, [page])

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
            <Flex direction="column" w="100%" alignItems="center" p={4}>
                <CandidatesHeader/>
                {candidates.map((candidate, index) => (
                    <CandidateCard
                        key={candidate.id}
                        candidate={candidate}
                        isOdd={index % 2 !== 0}
                        rowNum={page * pageSize + index + 1}
                        fetchCandidates={fetchCandidates}
                    />
                ))}

                <Box mt={4}>
                    <Button bg={'teal'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transform: 'translateY(-2px)',
                                boxShadow: 'lg'
                            }}
                            onClick={() => setPage(prev => prev - 1)}
                            disabled={page === 0}
                    >
                        Prethodna
                    </Button>
                    <Button bg={'teal'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transform: 'translateY(-2px)',
                                boxShadow: 'lg'
                            }}
                            onClick={() => setPage(prev => prev + 1)}
                            ml={2}
                    >
                        Sledeca
                    </Button>
                </Box>
            </Flex>
        </Simple>
    );
};

export default CandidateList;