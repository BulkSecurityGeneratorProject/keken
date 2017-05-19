import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Commentaire } from './commentaire.model';
import { CommentaireService } from './commentaire.service';
@Injectable()
export class CommentairePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private commentaireService: CommentaireService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.commentaireService.find(id).subscribe((commentaire) => {
                this.commentaireModalRef(component, commentaire);
            });
        } else {
            return this.commentaireModalRef(component, new Commentaire());
        }
    }

    commentaireModalRef(component: Component, commentaire: Commentaire): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.commentaire = commentaire;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
