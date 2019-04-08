import {Injectable} from '@angular/core';
import {RxStompService} from "@stomp/ng2-stompjs";

type StompCallback<T> = (response: T) => void

@Injectable({
    providedIn: 'root'
})
export class StompService {

    constructor(private rxStompService: RxStompService) {
    }

    publish(destination: string, body: any) {
        console.log("StompService :" + destination + ":", body)
        this.rxStompService.stompClient.publish({destination, body: body})
    }

    subscribePersonal<T>(destination: string, token: string, callback: StompCallback<T>) {
        return this.rxStompService.connected$
            .subscribe(value => {
                console.log("subscribePersonal " + `${destination}@${token}`);
                return this.rxStompService.stompClient.subscribe(`${destination}@${token}`, message => {
                    let object;
                    try {
                        object = JSON.parse(message.body);
                    } catch (e) {
                        object = message.body
                    }
                    callback(object);
                })
            })
    }

    subscribeGlobal<T>(destination: string, callback: StompCallback<T>) {
        return this.rxStompService.connected$
            .subscribe(value => {
                console.log("subscribeGlobal " + `${destination}`);
                return this.rxStompService.stompClient.subscribe(destination, message => {
                    let object;
                    try {
                        object = JSON.parse(message.body);
                    } catch (e) {
                        object = message.body
                    }
                    callback(object);
                })
            })
    }
}
