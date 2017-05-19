
const enum TypeBiere {
    'BLONDE',
    'BRUNE',
    'AMBRE'

};
import { UserExtra } from '../user-extra';
import { Commentaire } from '../commentaire';
export class Biere {
    constructor(
        public id?: number,
        public nom?: string,
        public type?: TypeBiere,
        public degreeAlc?: number,
        public brasseur?: string,
        public pays?: string,
        public ville?: string,
        public image?: string,
        public usersFavs?: UserExtra,
        public usersLikes?: UserExtra,
        public commentaires?: Commentaire,
    ) {
    }
}
